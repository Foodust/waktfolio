package waktfolio.domain.repository.content;

import org.springframework.data.jpa.repository.JpaRepository;
import waktfolio.domain.entity.content.Content;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ContentRepository extends JpaRepository<Content, UUID>, ContentCustomRepository {
    List<Content> findByMemberIdAndTagInAndUseYnOrderByTagAscCreateDateDesc(UUID memberId, List<String> tag,Boolean useYn);
    Optional<Content> findByMemberIdAndIdAndUseYn(UUID memberId, UUID id,Boolean useYn);
    Optional<Content> findByIdAndUseYn(UUID memberId, Boolean useYn);
}
