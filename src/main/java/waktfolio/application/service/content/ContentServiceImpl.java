package waktfolio.application.service.content;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import waktfolio.application.mapper.content.ContentMapper;
import waktfolio.application.mapper.count.CountMapper;
import waktfolio.domain.entity.content.Content;
import waktfolio.domain.entity.like.DayLike;
import waktfolio.domain.entity.like.MemberLike;
import waktfolio.domain.entity.log.LikeLog;
import waktfolio.domain.entity.log.ViewLog;
import waktfolio.domain.entity.member.Member;
import waktfolio.domain.entity.view.ContentView;
import waktfolio.domain.entity.view.DayView;
import waktfolio.domain.repository.content.ContentRepository;
import waktfolio.domain.repository.like.DayLikeRepository;
import waktfolio.domain.repository.like.MemberLikeRepository;
import waktfolio.domain.repository.log.LikeLogRepository;
import waktfolio.domain.repository.log.ViewLogRepository;
import waktfolio.domain.repository.member.MemberRepository;
import waktfolio.domain.repository.view.ContentViewRepository;
import waktfolio.domain.repository.view.DayViewRepository;
import waktfolio.exception.BusinessException;
import waktfolio.jwt.JwtTokenUtil;
import waktfolio.rest.dto.content.*;
import waktfolio.rest.dto.log.Count;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;
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
    private final LikeLogRepository likeLogRepository;
    private final ViewLogRepository viewLogRepository;
    @Override
    public void createContent(HttpServletRequest request,CreateContentRequest createContentRequest) {
        UUID memberId = UUID.fromString(jwtTokenUtil.getSubjectFromHeader(request));
        Content content = contentMapper.contentFrom(memberId,createContentRequest);
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
        if(!createDateContents.isEmpty())
            main.add(createDateContents.get(0));
        if(!orderByLikeCount.isEmpty())
            main.add(orderByLikeCount.get(0));
        if(!orderByViewCount.isEmpty())
            main.add(orderByViewCount.get(0));
        return contentMapper.findMainContentResponseOf(main, createDateContents, orderByLikeCount, orderByViewCount);
    }

    @Override
    public List<FindMemberResponse> findAllContentGroup(List<String> tags, Pageable pageable) {
        List<Content> contents = contentRepository.findByTagLikeIn(tags, pageable);
        Set<UUID> memberId = contents.stream().map(Content::getMemberId).collect(Collectors.toSet());
        List<Member> allGroups = memberRepository.findAllById(memberId);
        List<FindMemberResponse> findMemberResponse = new ArrayList<>();
        allGroups.forEach(member -> {
            Long totalLike = memberLikeRepository.countByMemberId(member.getId());
            Long totalView = contentRepository.sumViewByMemberId(member.getId());
            findMemberResponse.add(contentMapper.findMemberResponseOf(member, totalLike, totalView));
        });
        return findMemberResponse;
    }

    @Override
    public List<FindContentResponse> getContentGroup(UUID contentGroupId, List<String> tags) {
        List<Content> contents = contentRepository.findByMemberIdAndTagNameInAndUseYnOrderByTagNameAscCreateDateDesc(contentGroupId, tags,true);
        List<FindContentResponse> findContentResponses = new ArrayList<>();
        contents.forEach(content -> {
            Long likes = memberLikeRepository.countByContentId(content.getId());
            findContentResponses.add(contentMapper.findContentResponseOf(content, likes));
        });
        return findContentResponses;
    }

    @Override
    public FindContentDetailResponse getContent(UUID contentGroupId, UUID contentId) {
        Content content = contentRepository.findByMemberIdAndIdAndUseYn(contentGroupId, contentId,true).orElseThrow(BusinessException::NOT_FOUND_CONTENT);
        Long likes = memberLikeRepository.countByContentId(contentId);
        return contentMapper.findContentDetailResponseOf(content, likes);
    }

    @Override
    public void viewContent(UUID contentId) {
        contentRepository.findByIdAndUseYn(contentId,true).orElseThrow(BusinessException::NOT_FOUND_CONTENT);
        DayView dayView = dayViewRepository.findByContentId(contentId).orElse(DayView.builder().contentId(contentId).viewCount(0L).build());
        ContentView contentView = contentViewRepository.findByContentId(contentId).orElse(ContentView.builder().contentId(contentId).viewCount(0L).build());
        dayView.setViewCount(dayView.getViewCount() + 1);
        contentView.setViewCount(contentView.getViewCount() + 1);
        dayViewRepository.save(dayView);
        contentViewRepository.save(contentView);
    }

    @Override
    public LikeResponse upLikeContent(HttpServletRequest request, UUID contentId) {
        UUID memberId = UUID.fromString(jwtTokenUtil.getSubjectFromHeader(request));
        MemberLike memberLike = memberLikeRepository.findByMemberIdAndContentId(memberId, contentId);
        LikeResponse likeResponse = new LikeResponse();
        if (memberLike != null) {
            memberLikeRepository.delete(memberLike);
            likeResponse.setIsLike(false);
        } else {
            MemberLike memberLikeOn = contentMapper.memberLikeOf(memberId, contentId);
            memberLikeRepository.save(memberLikeOn);
            likeResponse.setIsLike(true);
        }
        Long byContentId = memberLikeRepository.countByContentId(contentId);
        likeResponse.setTotalLikeCount(byContentId);
        return likeResponse;
    }


    @Scheduled(cron = "0 0 0 * * *")
    public void getDayLike() {
        List<Count> counts = dayLikeRepository.countAllLike();
        List<LikeLog> list = counts.stream().map(countMapper::likeLogFrom).toList();
        likeLogRepository.saveAll(list);
        dayLikeRepository.deleteAll();
    }

    @Scheduled(cron = "0 0 0 * * *")
    public void getDayView() {
        List<Count> counts = dayViewRepository.countAllView();
        List<ViewLog> list = counts.stream().map(countMapper::viewLogFrom).toList();
        viewLogRepository.saveAll(list);
        dayViewRepository.deleteAll();
    }
}
