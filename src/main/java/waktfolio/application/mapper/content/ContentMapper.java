package waktfolio.application.mapper.content;

import org.springframework.stereotype.Component;
import waktfolio.domain.entity.content.Content;
import waktfolio.domain.entity.like.MemberLike;
import waktfolio.rest.dto.content.FindContentDetail;

import java.util.UUID;

@Component
public class ContentMapper {
    public MemberLike memberLikeOf(UUID memberId, UUID contentId){
        return MemberLike.builder()
                .memberId(memberId)
                .contentId(contentId)
                .build();
    }
    public FindContentDetail findContentResponseFrom(Content content){
        return FindContentDetail.builder()
                .name(content.getName())
                .description(content.getDescription())
                .likes(content.getLikes())
                .views(content.getViews())
                .tags(content.getTags())
                .backGroundColorCode(content.getBackGroundColorCode())
                .backGroundPath(content.getBackGroundPath())
                .cafeLink(content.getCafeLink())
                .objectPath(content.getObjectPath())
                .youtubeLink(content.getYoutubeLink())
                .contentId(content.getId())
                .build();
    }
}
