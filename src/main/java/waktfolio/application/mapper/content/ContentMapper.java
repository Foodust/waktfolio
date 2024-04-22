package waktfolio.application.mapper.content;

import org.springframework.stereotype.Component;
import waktfolio.domain.entity.content.Content;
import waktfolio.domain.entity.like.MemberLike;
import waktfolio.domain.entity.member.Member;
import waktfolio.rest.dto.content.FindContentDetailResponse;
import waktfolio.rest.dto.content.FindContentResponse;
import waktfolio.rest.dto.content.FindMemberResponse;

import java.util.UUID;

@Component
public class ContentMapper {
    public MemberLike memberLikeOf(UUID memberId, UUID contentId){
        return MemberLike.builder()
                .memberId(memberId)
                .contentId(contentId)
                .build();
    }
    public FindContentDetailResponse findContentDetailResponseOf(Content content, Long likes){
        return FindContentDetailResponse.builder()
                .name(content.getName())
                .description(content.getDescription())
                .likes(likes)
                .views(content.getViews())
                .tag(content.getTag())
                .backGroundColorCode(content.getBackGroundColorCode())
                .backGroundPath(content.getBackGroundPath())
                .cafeLink(content.getCafeLink())
                .objectPath(content.getObjectPath())
                .youtubeLink(content.getYoutubeLink())
                .contentId(content.getId())
                .build();
    }
    public FindMemberResponse findMemberResponseOf(Member member,Long likes,Long views){
        return FindMemberResponse.builder()
                .memberName(member.getName())
                .memberId(member.getId())
                .thumbnailImagePath(member.getProfileImagePath())
                .likes(likes)
                .views(views)
                .build();
    }
    public FindContentResponse findContentResponseOf(Content content,Long likes){
        return FindContentResponse.builder()
                .contentId(content.getId())
                .views(content.getViews())
                .likes(likes)
                .description(content.getDescription())
                .thumbnailImagePath(content.getThumbnailImagePath())
                .build();
    }
}
