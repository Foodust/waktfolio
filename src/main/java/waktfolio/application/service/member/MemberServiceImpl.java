package waktfolio.application.service.member;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import com.amazonaws.util.IOUtils;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import waktfolio.application.mapper.member.MemberMapper;
import waktfolio.domain.entity.member.Member;
import waktfolio.domain.repository.content.ContentRepository;
import waktfolio.domain.repository.like.MemberLikeRepository;
import waktfolio.domain.repository.member.MemberRepository;
import waktfolio.exception.BusinessException;
import waktfolio.jwt.JwtTokenUtil;
import waktfolio.rest.dto.member.*;

import javax.swing.text.Utilities;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {
    private final MemberMapper memberMapper;
    private final MemberRepository memberRepository;
    private final ContentRepository contentRepository;
    private final MemberLikeRepository memberLikeRepository;
    private final JwtTokenUtil jwtTokenUtil;
    private final PasswordEncoder passwordEncoder;

    private final AmazonS3Client amazonS3Client;
    private final String profileImagePrefix = "profileImage/";
    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    @Override
    public LoginMemberResponse login(LoginMemberRequest loginMemberRequest) throws IOException {
        Member member = memberRepository.findByLoginId(loginMemberRequest.getLoginId()).orElseThrow(BusinessException::NOT_FOUND_MEMBER);
        if (!passwordEncoder.matches(loginMemberRequest.getPassword(), member.getPassword())) {
            throw BusinessException.NOT_MATCHED_PASSWORD();
        }
        String newToken = jwtTokenUtil.generateToken(member.getId());
        return memberMapper.loginMemberResponseOf(member.getName(), newToken, getFile(member.getProfileImagePath()));
    }

    @Override
    public void update(HttpServletRequest request, UpdateMemberRequest updateMemberRequest) {
        UUID memberId = UUID.fromString(jwtTokenUtil.getSubjectFromHeader(request));
        Member member = memberRepository.findById(memberId).orElseThrow(BusinessException::NOT_FOUND_MEMBER);
        if (updateMemberRequest.getProfileImage() != null)
            member.setProfileImagePath(uploadFile(profileImagePrefix + member.getId() + "/", updateMemberRequest.getProfileImage()));
        memberMapper.memberUpdateFrom(member, updateMemberRequest);
    }

    @Override
    public void register(RegisterMemberRequest registerMemberRequest) {
        memberRepository.findByLoginId(registerMemberRequest.getLoginId()).ifPresent(a -> {
            throw BusinessException.ALREADY_EXISTS_USER_ID();
        });
        String encodePassword = passwordEncoder.encode(registerMemberRequest.getPassword());
        Member member = memberMapper.memberFrom(registerMemberRequest, encodePassword);
        String fileName = uploadFile(profileImagePrefix + member.getId() + "/", registerMemberRequest.getProfileImage());
        member.setProfileImagePath(fileName);
        memberRepository.save(member);
    }

    @Override
    public MemberProfileResponse profile(HttpServletRequest request) {
        UUID memberId = UUID.fromString(jwtTokenUtil.getSubjectFromHeader(request));
        Member member = memberRepository.findById(memberId).orElseThrow(BusinessException::NOT_FOUND_MEMBER);
        Long totalLike = memberLikeRepository.countByMemberId(memberId);
        Long totalView = contentRepository.sumViewByMemberId(memberId);
        return memberMapper.memberProfileResponseOf(member, totalLike, totalView);
    }

    private String getFile(String fileName) throws IOException {

        String folderPath = fileName.substring(0, fileName.lastIndexOf("/") + 1);
        String fileOnlyName = fileName.substring(fileName.lastIndexOf("/") + 1);
        String filePath = "/file/" + folderPath;
        String resultPath = filePath + fileOnlyName;
        Path path = Paths.get(filePath);
        if (!Files.exists(path)) {
            Files.createDirectories(path);
        }
        File file = new File(resultPath);
        if (!file.exists()) {
            S3Object object = amazonS3Client.getObject(new GetObjectRequest(bucket, fileName));
            try (S3ObjectInputStream objectInputStream = ((S3Object) object).getObjectContent(); FileOutputStream fileStream = new FileOutputStream(file)) {
                byte[] buffer = IOUtils.toByteArray(objectInputStream);
                fileStream.write(buffer, 0, buffer.length);
            }
        }
        boolean b = file.setReadOnly();
        return file.getPath();
    }

    private String uploadFile(String path, MultipartFile file) {
        try {
            String fileName = path + file.getOriginalFilename();
            ObjectMetadata metadata = new ObjectMetadata();
            metadata.setContentType(file.getContentType());
            metadata.setContentLength(file.getSize());
            amazonS3Client.createBucket(bucket);
            amazonS3Client.putObject(bucket, fileName, file.getInputStream(), metadata);
            return fileName;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
