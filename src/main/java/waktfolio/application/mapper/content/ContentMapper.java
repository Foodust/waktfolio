package waktfolio.application.mapper.content;

import org.springframework.stereotype.Component;
import waktfolio.domain.entity.content.Content;
import waktfolio.domain.entity.like.DayLike;
import waktfolio.domain.entity.like.MemberLike;
import waktfolio.domain.entity.member.Member;
import waktfolio.rest.dto.content.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
public class ContentMapper {
    public MemberLike memberLikeOf(UUID memberId, UUID contentId) {
        return MemberLike.builder()
                .memberId(memberId)
                .contentId(contentId)
                .build();
    }

    public DayLike dayLikeOf(UUID memberId, UUID contentId) {
        return DayLike.builder()
                .memberId(memberId)
                .contentId(contentId)
                .build();
    }

    public Content contentFrom(UUID memberId, CreateContentRequest createContentRequest) {
        return Content.builder()
                .memberId(memberId)
                .title(createContentRequest.getName())
                .description(createContentRequest.getDescription())
                .tagId(createContentRequest.getTagId())
                .backGroundColorCode(createContentRequest.getBackGroundColorCode())
                .cafeLink(createContentRequest.getCafeLink())
                .youtubeLink(createContentRequest.getYoutubeLink())
                .useYn(false)
                .build();
    }

    public FindContentDetailResponse findContentDetailResponseOf(FindContentDetail findContentDetail, Long likes, Long views, Boolean isLike) {
        return FindContentDetailResponse.builder()
                .name(findContentDetail.getTitle())
                .description(findContentDetail.getDescription())
                .likes(likes)
                .views(views)
                .tagName(findContentDetail.getTagName())
                .backGroundColorCode(findContentDetail.getBackGroundColorCode())
                .backGroundPath(findContentDetail.getBackGroundPath())
                .cafeLink(findContentDetail.getCafeLink())
                .objectPath(findContentDetail.getObjectPath())
                .youtubeLink(findContentDetail.getYoutubeLink())
                .contentId(findContentDetail.getId())
                .isLike(isLike)
                .build();
    }

    public FindContentDetailResponse findContentDetailResponseOf(FindContentDetail findContentDetail) {
        return FindContentDetailResponse.builder()
                .name(findContentDetail.getTitle())
                .description(findContentDetail.getDescription())
                .tagName(findContentDetail.getTagName())
                .backGroundColorCode(findContentDetail.getBackGroundColorCode())
                .backGroundPath(findContentDetail.getBackGroundPath())
                .cafeLink(findContentDetail.getCafeLink())
                .objectPath(findContentDetail.getObjectPath())
                .youtubeLink(findContentDetail.getYoutubeLink())
                .contentId(findContentDetail.getId())
                .build();
    }

    public FindMember findMemberResponseOf(Member member) {
        return FindMember.builder()
                .memberName(member.getName())
                .memberId(member.getId())
                .thumbnailImagePath(member.getProfileImagePath())
                .build();
    }

    public FindContentResponse findContentResponseOf(Content content, Long likes, Long views) {
        return FindContentResponse.builder()
                .contentId(content.getId())
                .title(content.getTitle())
                .likes(likes)
                .views(views)
                .description(content.getDescription())
                .thumbnailImagePath(content.getThumbnailImagePath())
                .build();
    }

    public FindContent findContentOf(Content content,String memberName, Long likes, Long views) {
        return FindContent.builder()
                .contentId(content.getId())
                .title(content.getTitle())
                .memberName(memberName)
                .thumbnailImagePath(content.getThumbnailImagePath())
                .likes(likes)
                .views(views)
                .build();
    }

    public FindMainContentResponse findMainContentResponseOf(List<FindContent> main, List<FindContent> createDate, List<FindContent> like, List<FindContent> view) {
        return FindMainContentResponse.builder()
                .mainContent(main)
                .newContent(createDate)
                .likeContent(like)
                .viewContent(view)
                .build();
    }

    public FindAllContentResponse findAllContentResponseOf(List<FindContent> findContent, List<FindMember> findMember) {
        return FindAllContentResponse.builder()
                .groups(findMember)
                .contents(findContent)
                .build();
    }

    public void updateContent(Content content, UpdateContentRequest updateContentRequest) {
        Optional.ofNullable(updateContentRequest.getName()).ifPresent(content::setTitle);
        Optional.ofNullable(updateContentRequest.getTagId()).ifPresent(content::setTagId);
        Optional.ofNullable(updateContentRequest.getDescription()).ifPresent(content::setDescription);
        Optional.ofNullable(updateContentRequest.getBackGroundColorCode()).ifPresent(content::setBackGroundColorCode);
        Optional.ofNullable(updateContentRequest.getCafeLink()).ifPresent(content::setCafeLink);
        Optional.ofNullable(updateContentRequest.getYoutubeLink()).ifPresent(content::setYoutubeLink);
        content.setUseYn(false);
    }
}
