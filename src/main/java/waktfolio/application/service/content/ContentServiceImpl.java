package waktfolio.application.service.content;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import waktfolio.application.mapper.content.ContentMapper;
import waktfolio.domain.entity.content.Content;
import waktfolio.domain.entity.content.ContentGroup;
import waktfolio.domain.entity.like.MemberLike;
import waktfolio.domain.repository.content.ContentGroupRepository;
import waktfolio.domain.repository.content.ContentRepository;
import waktfolio.domain.repository.like.DayLikeRepository;
import waktfolio.domain.repository.like.MemberLikeRepository;
import waktfolio.domain.repository.view.DayViewRepository;
import waktfolio.domain.repository.view.MemberViewRepository;
import waktfolio.exception.BusinessException;
import waktfolio.jwt.JwtTokenUtil;
import waktfolio.rest.dto.BaseListDto;
import waktfolio.rest.dto.content.*;

import java.util.List;
import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class ContentServiceImpl implements ContentService {
    private final JwtTokenUtil jwtTokenUtil;
    private final ContentMapper contentMapper;
    private final ContentRepository contentRepository;
    private final DayLikeRepository dayLikeRepository;
    private final DayViewRepository dayViewRepository;
    private final ContentGroupRepository contentGroupRepository;
    private final MemberLikeRepository memberLikeRepository;
    private final MemberViewRepository memberViewRepository;
    @Override
    public MainContentResponse getMainContent() {
        return null;
    }

    @Override
    public List<FindContentGroupResponse> getAllContentGroup(List<String> tags) {
        List<Content> contents = contentRepository.findByTagLikeIn(tags);
        List<UUID> contentGroupIds = contents.stream().map(Content::getContentGroupId).toList();
        List<ContentGroup> contentGroups = contentGroupRepository.findByIdIn(contentGroupIds);
        return null;
    }

    @Override
    public List<FindContentDetail> getAllContent(UUID contentGroupId,List<String> tags) {
        List<Content> contents = contentRepository.findByContentGroupIdAndTagInOrderByTagAscCreateDateDesc(contentGroupId, tags);
        return contents.stream().map(contentMapper::findContentResponseFrom).toList();
    }

    @Override
    public LikeResponse upLikeContent(HttpServletRequest request, UUID contentId) {
        UUID memberId = UUID.fromString(jwtTokenUtil.getSubjectFromHeader(request));
        MemberLike memberLike = memberLikeRepository.findByMemberIdAndContentId(memberId, contentId);
        Content content = contentRepository.findById(contentId).orElseThrow(BusinessException::NOT_FOUND_CONTENT);
        LikeResponse likeResponse = new LikeResponse();
        if(memberLike != null){
            memberLikeRepository.delete(memberLike);
            content.setLikes(content.getLikes() - 1);
            likeResponse.setIsLike(false);
        }
        else{
            MemberLike memberLikeOn = contentMapper.memberLikeOf(memberId, contentId);
            memberLikeRepository.save(memberLikeOn);
            content.setLikes(content.getLikes() + 1);
            likeResponse.setIsLike(true);
        }
        likeResponse.setTotalLikeCount(content.getLikes());
        return likeResponse;
    }


    @Scheduled(cron = "0 0 0 * * *")
    public void getDayLike(){

    }
    @Scheduled(cron = "0 0 0 * * *")
    public void getDayView(){

    }
}
