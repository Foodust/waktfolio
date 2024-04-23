package waktfolio.domain.repository.like;

import waktfolio.rest.dto.content.FindContent;
import waktfolio.rest.dto.log.Count;

import java.util.List;

public interface DayLikeCustomRepository {
    List<FindContent> findOrderByLikeCount();
    List<Count> countAllLike();
}
