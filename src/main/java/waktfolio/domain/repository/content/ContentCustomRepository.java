package waktfolio.domain.repository.content;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import waktfolio.domain.entity.content.Content;
import waktfolio.rest.dto.BaseListDto;
import waktfolio.rest.dto.content.FindContent;

import java.util.List;
import java.util.UUID;

public interface ContentCustomRepository {
    Long sumViewByMemberId(UUID memberId);
    List<Content> findByTagLikeIn(List<String> tags,Pageable pageable);

    List<FindContent> findOrderByCreateDate();
}
