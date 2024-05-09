package waktfolio.domain.repository.content;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import waktfolio.domain.entity.content.Content;
import waktfolio.rest.dto.BaseListDto;
import waktfolio.rest.dto.content.FindContent;
import waktfolio.rest.dto.content.FindContentDetail;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ContentCustomRepository {
    List<Content> findByMemberIdAndUseYnOrderByTagNameAscCreateDateDesc(UUID memberId, Boolean useYn);
    Optional<FindContentDetail> findByIdAndUseYn(UUID contentId, Boolean useYn);
    List<Content> findByTagLikeIn(List<String> tags,Pageable pageable);
    List<FindContent> findOrderByCreateDate();
    List<FindContentDetail> findByUseYn(Boolean useYn, Pageable pageable);
}
