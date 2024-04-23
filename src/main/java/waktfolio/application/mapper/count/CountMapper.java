package waktfolio.application.mapper.count;

import org.springframework.stereotype.Component;
import waktfolio.domain.entity.log.LikeLog;
import waktfolio.domain.entity.log.ViewLog;
import waktfolio.rest.dto.log.Count;

@Component
public class CountMapper {
    public LikeLog likeLogFrom(Count count){
        return LikeLog.builder()
                .contentId(count.getContentId())
                .likeCount(count.getCount())
                .build();
    }
    public ViewLog viewLogFrom(Count count){
        return ViewLog.builder()
                .contentId(count.getContentId())
                .viewCount(count.getCount())
                .build();
    }
}
