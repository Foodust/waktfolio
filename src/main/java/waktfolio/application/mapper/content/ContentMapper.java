package waktfolio.application.mapper.content;

import org.springframework.stereotype.Component;
import waktfolio.domain.entity.content.Content;
import waktfolio.domain.entity.like.MemberLike;
import waktfolio.domain.entity.member.Member;
import waktfolio.rest.dto.content.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Component
public class ContentMapper {
    public MemberLike memberLikeOf(UUID memberId, UUID contentId) {
        return MemberLike.builder()
                .memberId(memberId)
                .contentId(contentId)
                .build();
    }

    public FindContentDetailResponse findContentDetailResponseOf(Content content, Long likes) {
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
                .views(content.getViews())
                .likes(likes)
                .description(content.getDescription())
                .thumbnailImagePath(content.getThumbnailImagePath())
                .build();
    }

    public FindMainContentResponse findMainContentResponseOf(List<FindContent> createDate, List<FindContent> like, List<FindContent> view) {
        return FindMainContentResponse.builder()
                .mainMember(findContentsOf(createDate.get(0), like.get(0), view.get(0)))
                .newMember(createDate)
                .likeMember(like)
                .viewMember(view)
                .build();
    }

    private List<FindContent> findContentsOf(FindContent create, FindContent like, FindContent view) {
        List<FindContent> contents = new ArrayList<>();
        contents.add(create);
        contents.add(like);
        contents.add(view);
        return contents;
    }
}
