package waktfolio.domain.repository.like;

import waktfolio.rest.dto.content.FindContent;

import java.util.List;

public interface DayLikeCustomRepository {
    List<FindContent> findOrderByLikeCount();
}
