package waktfolio.application.mapper.content;

import org.springframework.stereotype.Component;
import waktfolio.domain.entity.content.Content;
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
    public Content contentFrom(UUID memberId, CreateContentRequest createContentRequest){
        return Content.builder()
                .memberId(memberId)
                .name(createContentRequest.getName())
                .description(createContentRequest.getDescription())
                .tagName(createContentRequest.getTagName())
                .backGroundColorCode(createContentRequest.getBackGroundColorCode())
                .cafeLink(createContentRequest.getCafeLink())
                .youtubeLink(createContentRequest.getYoutubeLink())
                .useYn(false)
                .build();
    }

    public FindContentDetailResponse findContentDetailResponseOf(Content content, Long likes) {
        return FindContentDetailResponse.builder()
                .name(content.getName())
                .description(content.getDescription())
                .likes(likes)
                .tagName(content.getTagName())
                .backGroundColorCode(content.getBackGroundColorCode())
                .backGroundPath(content.getBackGroundPath())
                .cafeLink(content.getCafeLink())
                .objectPath(content.getObjectPath())
                .youtubeLink(content.getYoutubeLink())
                .contentId(content.getId())
                .build();
    }

    public FindMemberResponse findMemberResponseOf(Member member, Long likes, Long views) {
        return FindMemberResponse.builder()
                .memberName(member.getName())
                .memberId(member.getId())
                .thumbnailImagePath(member.getProfileImagePath())
                .likes(likes)
                .views(views)
                .build();
    }

    public FindContentResponse findContentResponseOf(Content content, Long likes) {
        return FindContentResponse.builder()
                .contentId(content.getId())
                .likes(likes)
                .description(content.getDescription())
                .thumbnailImagePath(content.getThumbnailImagePath())
                .build();
    }
    public FindContentResponse findContentResponseOf(Content content) {
        return FindContentResponse.builder()
                .contentId(content.getId())
                .description(content.getDescription())
                .thumbnailImagePath(content.getThumbnailImagePath())
                .build();
    }
    public FindMainContentResponse findMainContentResponseOf(List<FindContent> main, List<FindContent> createDate, List<FindContent> like, List<FindContent> view) {
        return FindMainContentResponse.builder()
                .mainMember(main)
                .newMember(createDate)
                .likeMember(like)
                .viewMember(view)
                .build();
    }
    public void updateContent(Content content, UpdateContentRequest updateContentRequest){
        Optional.ofNullable(updateContentRequest.getName()).ifPresent(content::setName);
        Optional.ofNullable(updateContentRequest.getDescription()).ifPresent(content::setDescription);
        Optional.ofNullable(updateContentRequest.getBackGroundColorCode()).ifPresent(content::setBackGroundColorCode);
        Optional.ofNullable(updateContentRequest.getCafeLink()).ifPresent(content::setCafeLink);
        Optional.ofNullable(updateContentRequest.getYoutubeLink()).ifPresent(content::setYoutubeLink);
        content.setUseYn(false);
    }
}
