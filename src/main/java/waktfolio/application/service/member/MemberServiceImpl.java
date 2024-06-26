package waktfolio.application.service.member;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.*;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import waktfolio.application.mapper.member.MemberMapper;
import waktfolio.domain.entity.content.Content;
import waktfolio.domain.entity.content.Tag;
import waktfolio.domain.entity.like.DayLike;
import waktfolio.domain.entity.like.MemberLike;
import waktfolio.domain.entity.member.Member;
import waktfolio.domain.repository.content.ContentRepository;
import waktfolio.domain.repository.like.DayLikeRepository;
import waktfolio.domain.repository.like.MemberLikeRepository;
import waktfolio.domain.repository.member.MemberRepository;
import waktfolio.domain.repository.tag.TagRepository;
import waktfolio.domain.repository.view.ContentViewRepository;
import waktfolio.domain.repository.view.DayViewRepository;
import waktfolio.exception.BusinessException;
import waktfolio.jwt.JwtTokenUtil;
import waktfolio.rest.dto.member.*;

import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {
    private final MemberMapper memberMapper;
    private final MemberRepository memberRepository;
    private final ContentRepository contentRepository;
    private final MemberLikeRepository memberLikeRepository;
    private final DayLikeRepository dayLikeRepository;
    private final ContentViewRepository contentViewRepository;
    private final TagRepository tagRepository;

    private final JwtTokenUtil jwtTokenUtil;

    private final PasswordEncoder passwordEncoder;

    private final AmazonS3Client amazonS3Client;
    private final String profileImagePrefix = "profileImage";
    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    @Override
    public LoginMemberResponse loginMember(LoginMemberRequest loginMemberRequest) {
        Member member = memberRepository.findByLoginId(loginMemberRequest.getLoginId()).orElseThrow(BusinessException::NOT_FOUND_MEMBER);
        if (!passwordEncoder.matches(loginMemberRequest.getPassword(), member.getPassword())) {
            throw BusinessException.NOT_MATCHED_PASSWORD();
        }
        String newToken = jwtTokenUtil.generateToken(member.getId());
        return memberMapper.loginMemberResponseOf(member.getName(), newToken, member.getProfileImagePath());
    }

    @Override
    public void updateMember(HttpServletRequest request, UpdateMemberRequest updateMemberRequest) {
        UUID memberId = UUID.fromString(jwtTokenUtil.getSubjectFromHeader(request));
        Member member = memberRepository.findById(memberId).orElseThrow(BusinessException::NOT_FOUND_MEMBER);
        if (updateMemberRequest.getProfileImage() != null) {
            String fileName = uploadProfileImageFile(member.getId() + "/" + profileImagePrefix, profileImagePrefix, updateMemberRequest.getProfileImage());
            member.setProfileImagePath(fileName);
        }
        memberMapper.memberUpdateFrom(member, updateMemberRequest);
        memberRepository.save(member);
    }

    @Override
    public void deleteMember(HttpServletRequest request) {
        UUID memberId = UUID.fromString(jwtTokenUtil.getSubjectFromHeader(request));
        Member member = memberRepository.findById(memberId).orElseThrow(BusinessException::NOT_FOUND_MEMBER);
        List<Content> contents = contentRepository.findByMemberId(memberId);
        List<Tag> tags = tagRepository.findByMemberIdOrderByName(memberId);
        List<MemberLike> memberLikes = memberLikeRepository.findByMemberId(memberId);
        List<DayLike> dayLikes = dayLikeRepository.findByMemberId(memberId);
        deleteAwsFile(memberId.toString());
        dayLikeRepository.deleteAll(dayLikes);
        memberLikeRepository.deleteAll(memberLikes);
        tagRepository.deleteAll(tags);
        contentRepository.deleteAll(contents);
        memberRepository.delete(member);
    }

    @Override
    public void registerMember(RegisterMemberRequest registerMemberRequest) {
        memberRepository.findByLoginId(registerMemberRequest.getLoginId()).ifPresent(a -> {
            throw BusinessException.ALREADY_EXISTS_USER_ID();
        });
        memberRepository.findByName(registerMemberRequest.getName()).ifPresent(a -> {
            throw BusinessException.ALREADY_EXITS_NAME();
        });
        String encodePassword = passwordEncoder.encode(registerMemberRequest.getPassword());
        Member member = memberMapper.memberFrom(registerMemberRequest, encodePassword);
        if (registerMemberRequest.getProfileImage() != null) {
            String fileName = uploadProfileImageFile(member.getId() + "/" + profileImagePrefix, profileImagePrefix, registerMemberRequest.getProfileImage());
            member.setProfileImagePath(fileName);
        }
        memberRepository.save(member);
    }

    @Override
    public MemberProfileResponse profileMember(HttpServletRequest request) {
        UUID memberId = UUID.fromString(jwtTokenUtil.getSubjectFromHeader(request));
        Member member = memberRepository.findById(memberId).orElseThrow(BusinessException::NOT_FOUND_MEMBER);
        return memberMapper.memberProfileResponseOf(member, getLikeMemberId(memberId), getViewMemberId(memberId));
    }

    private Long getLikeMemberId(UUID memberId) {
        return memberLikeRepository.countAddCountByMemberId(memberId);
    }

    private Long getViewMemberId(UUID memberId) {
        return contentViewRepository.sumAddSumByMemberId(memberId);
    }

    private String uploadProfileImageFile(String path, String name, MultipartFile file) {
        try {
            String fileName = path + "/" + name + Objects.requireNonNull(file.getOriginalFilename()).substring(file.getOriginalFilename().lastIndexOf("."));
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

    private void deleteAwsFile(String path) {
        ListObjectsRequest listObjectsRequest = new ListObjectsRequest()
                .withBucketName(bucket)
                .withPrefix(path + "/");
        ObjectListing objectListing = amazonS3Client.listObjects(listObjectsRequest);
        List<S3ObjectSummary> objectSummaries = objectListing.getObjectSummaries();
        for (S3ObjectSummary objectSummary : objectSummaries) {
            String key = objectSummary.getKey();
            amazonS3Client.deleteObject(bucket, key);
        }
    }
}
