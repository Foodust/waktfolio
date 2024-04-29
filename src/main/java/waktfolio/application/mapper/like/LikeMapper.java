package waktfolio.application.mapper.like;

import org.springframework.stereotype.Component;
import waktfolio.domain.entity.like.DayLike;
import waktfolio.domain.entity.like.MemberLike;

@Component
public class LikeMapper {
    public MemberLike memberLikeFrom(DayLike dayLike){
        return MemberLike.builder()
                .memberId(dayLike.getMemberId())
                .contentId(dayLike.getContentId())
                .createDate(dayLike.getCreateDate())
                .updateDate(dayLike.getUpdateDate())
                .build();
    }
}
