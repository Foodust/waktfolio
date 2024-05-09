package waktfolio.application.service.content;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.ObjectMetadata;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import waktfolio.application.mapper.content.ContentMapper;
import waktfolio.application.mapper.count.CountMapper;
import waktfolio.application.mapper.like.LikeMapper;
import waktfolio.application.mapper.view.ContentViewMapper;
import waktfolio.domain.entity.content.Content;
import waktfolio.domain.entity.like.DayLike;
import waktfolio.domain.entity.like.MemberLike;
import waktfolio.domain.entity.member.Member;
import waktfolio.domain.entity.view.ContentView;
import waktfolio.domain.entity.view.DayView;
import waktfolio.domain.repository.content.ContentRepository;
import waktfolio.domain.repository.like.DayLikeRepository;
import waktfolio.domain.repository.like.MemberLikeRepository;
import waktfolio.domain.repository.member.MemberRepository;
import waktfolio.domain.repository.view.ContentViewRepository;
import waktfolio.domain.repository.view.DayViewRepository;
import waktfolio.exception.BusinessException;
import waktfolio.jwt.JwtTokenUtil;
import waktfolio.rest.dto.content.*;
import waktfolio.rest.dto.log.Count;

import java.io.IOException;
import java.math.BigInteger;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class ContentServiceImpl implements ContentService {
    private final JwtTokenUtil jwtTokenUtil;

    private final ContentMapper contentMapper;
    private final ContentRepository contentRepository;
    private final ContentViewRepository contentViewRepository;

    private final DayLikeRepository dayLikeRepository;
    private final DayViewRepository dayViewRepository;

    private final MemberRepository memberRepository;
    private final MemberLikeRepository memberLikeRepository;

    private final CountMapper countMapper;
    private final LikeMapper likeMapper;
    private final ContentViewMapper contentViewMapper;

    private final AmazonS3Client amazonS3Client;
    private final String objectName = "object";
    private final String thumbnailImage = "thumbnail";
    private final String backGround = "backGround";
    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    @Override
    public void createContent(HttpServletRequest request, CreateContentRequest createContentRequest) {
        UUID memberId = UUID.fromString(jwtTokenUtil.getSubjectFromHeader(request));
        Content content = contentMapper.contentFrom(memberId, createContentRequest);
        if (createContentRequest.getObject() == null) {
            throw BusinessException.NOT_EXITS_FILE();
        }
        String objectPath = uploadFile(memberId + "/" + objectName + "/" + createContentRequest.getName(), objectName, createContentRequest.getObject());
        content.setObjectPath(objectPath);
        if (createContentRequest.getBackGround() != null) {
            String backGroundPath = uploadFile(memberId + "/" + backGround + "/" + createContentRequest.getName(), backGround, createContentRequest.getBackGround());
            content.setBackGroundPath(backGroundPath);
        }
        if (createContentRequest.getThumbnailImage() != null) {
            String thumbnailImagePath = uploadFile(memberId + "/" + thumbnailImage + "/" + createContentRequest.getName(), thumbnailImage, createContentRequest.getThumbnailImage());
            content.setThumbnailImagePath(thumbnailImagePath);
        }
        contentRepository.save(content);
    }

    @Override
    public void updateContent(HttpServletRequest request, UpdateContentRequest updateContentRequest) {
        UUID memberId = UUID.fromString(jwtTokenUtil.getSubjectFromHeader(request));
        Content content = contentRepository.findById(updateContentRequest.getContentId()).orElseThrow(BusinessException::NOT_FOUND_CONTENT);
        contentMapper.updateContent(content, updateContentRequest);
        if (updateContentRequest.getObject() != null) {
            String objectPath = uploadFile(memberId + "/" + objectName + "/" + updateContentRequest.getName(), objectName, updateContentRequest.getObject());
            content.setObjectPath(objectPath);
        }
        if (updateContentRequest.getBackGround() != null) {
            String backGroundPath = uploadFile(memberId + "/" + backGround + "/" + updateContentRequest.getName(), backGround, updateContentRequest.getBackGround());
            content.setBackGroundPath(backGroundPath);
        }
        if (updateContentRequest.getThumbnailImage() != null) {
            String thumbnailImagePath = uploadFile(memberId + "/" + thumbnailImage + "/" + updateContentRequest.getName(), thumbnailImage, updateContentRequest.getThumbnailImage());
            content.setThumbnailImagePath(thumbnailImagePath);
        }
        contentRepository.save(content);
    }

    @Override
    public FindMainContentResponse getMainMember() {
        // 최근 등록 순
        List<FindContent> createDateContents = contentRepository.findOrderByCreateDate();
        // 좋아요 순
        List<FindContent> orderByLikeCount = dayLikeRepository.findOrderByLikeCount();
        // 조회 순
        List<FindContent> orderByViewCount = dayViewRepository.findOrderByViewCount();

        List<FindContent> main = new ArrayList<>();
        if (!createDateContents.isEmpty())
            main.add(createDateContents.get(0));
        if (!orderByLikeCount.isEmpty())
            main.add(orderByLikeCount.get(0));
        if (!orderByViewCount.isEmpty())
            main.add(orderByViewCount.get(0));
        return contentMapper.findMainContentResponseOf(main, createDateContents, orderByLikeCount, orderByViewCount);
    }

    @Override
    public List<FindMemberResponse> findAllContentGroup(List<String> tags, Pageable pageable) {
        List<Content> contents = contentRepository.findByTagLikeIn(tags != null ? tags : new ArrayList<>(), pageable);
        Set<UUID> memberIds = contents.stream().map(Content::getMemberId).collect(Collectors.toSet());
        List<Member> allGroups = memberRepository.findAllById(memberIds);
        List<FindMemberResponse> findMemberResponse = new ArrayList<>();
        allGroups.forEach(member -> {
            findMemberResponse.add(contentMapper.findMemberResponseOf(member, getLikeMemberId(member.getId()), getViewMemberId(member.getId())));
        });
        return findMemberResponse;
    }

    // 조회수 해야함 나중에 하삼
    @Override
    public List<FindContentResponse> getContentGroup(UUID memberId) {
        List<Content> contents = contentRepository.findByMemberIdAndUseYnOrderByTagNameAscCreateDateDesc(memberId, true);
        List<FindContentResponse> findContentResponses = new ArrayList<>();
        contents.forEach(content -> {
            findContentResponses.add(contentMapper.findContentResponseOf(content, getLikeContentId(content.getId()), getViewContentId(content.getId())));
        });
        return findContentResponses;
    }

    @Override
    public FindContentDetailResponse getContent(HttpServletRequest request, UUID contentId) {
        Content content = contentRepository.findByIdAndUseYn(contentId, true).orElseThrow(BusinessException::NOT_FOUND_CONTENT);
        AtomicReference<Boolean> isLike = new AtomicReference<>(false);
        if (jwtTokenUtil.getSubjectFromHeader(request) != null) {
            UUID seeMember = UUID.fromString(jwtTokenUtil.getSubjectFromHeader(request));
            memberLikeRepository.findByMemberIdAndContentId(seeMember, contentId).ifPresent(a -> isLike.set(true));
            dayLikeRepository.findByMemberIdAndContentId(seeMember, contentId).ifPresent(a -> isLike.set(true));
        }
        return contentMapper.findContentDetailResponseOf(content, getLikeContentId(contentId), getViewContentId(contentId), isLike.get());
    }

    @Override
    public ViewResponse viewContent(UUID contentId) {
        contentRepository.findByIdAndUseYn(contentId, true).orElseThrow(BusinessException::NOT_FOUND_CONTENT);
        DayView dayView = dayViewRepository.findByContentId(contentId).orElse(DayView.builder().contentId(contentId).viewCount(0L).build());
        dayView.setViewCount(dayView.getViewCount() + 1);
        dayViewRepository.save(dayView);
        return countMapper.viewResponseOf(getViewContentId(contentId));
    }

    @Override
    public LikeResponse upLikeContent(HttpServletRequest request, UUID contentId) {
        LikeResponse likeResponse = LikeResponse.builder().isLike(false).build();
        UUID memberId = UUID.fromString(jwtTokenUtil.getSubjectFromHeader(request));
        DayLike dayLike = dayLikeRepository.findByMemberIdAndContentId(memberId, contentId).orElse(null);
        if (dayLike == null) {
            MemberLike memberLike = memberLikeRepository.findByMemberIdAndContentId(memberId, contentId).orElse(null);
            if (memberLike == null) {
                DayLike dayLikeOn = contentMapper.dayLikeOf(memberId, contentId);
                dayLikeRepository.save(dayLikeOn);
                likeResponse.setIsLike(true);
            } else {
                memberLikeRepository.delete(memberLike);
            }
        } else {
            dayLikeRepository.delete(dayLike);
        }
        likeResponse.setTotalLikeCount(getLikeContentId(contentId));
        return likeResponse;
    }

    private String uploadFile(String path, String name, MultipartFile file) {
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

    private Long getLikeContentId(UUID contentId) {
        return memberLikeRepository.countAddCountByContentId(contentId).longValue();
    }

    private Long getLikeMemberId(UUID memberId) {
        return memberLikeRepository.countAddCountByMemberId(memberId);
    }

    private Long getViewContentId(UUID contentId) {
        return contentViewRepository.sumAddSumByContentId(contentId).longValue();
    }

    private Long getViewMemberId(UUID memberId) {
        return contentViewRepository.sumAddSumByMemberId(memberId);
    }

    @Scheduled(cron = "0 0 0 * * *")
    public void getDayLike() {
        List<DayLike> dayLikes = dayLikeRepository.findAll();
        memberLikeRepository.saveAll(dayLikes.stream().map(likeMapper::memberLikeFrom).toList());
        dayLikeRepository.deleteAll();
    }

    @Scheduled(cron = "0 0 0 * * *")
    public void getDayView() {
        List<Count> counts = dayViewRepository.countAllView();
        counts.forEach(count -> {
            ContentView contentView = contentViewRepository.findByContentId(count.getContentId()).orElse(contentViewMapper.contentView(count.getContentId(), 0L));
            contentView.setViewCount(contentView.getViewCount() + count.getCount());
            contentViewRepository.save(contentView);
        });
        dayViewRepository.deleteAll();
    }
}
