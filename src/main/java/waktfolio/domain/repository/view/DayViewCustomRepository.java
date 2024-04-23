package waktfolio.domain.repository.view;

import waktfolio.rest.dto.FindCount;
import waktfolio.rest.dto.content.FindContent;
import waktfolio.rest.dto.log.Count;

import java.util.List;

public interface DayViewCustomRepository {
    List<FindContent> findOrderByViewCount();
    List<Count> countAllView();
}
