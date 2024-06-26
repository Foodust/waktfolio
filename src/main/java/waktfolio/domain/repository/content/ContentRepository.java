package waktfolio.domain.repository.content;

import org.springframework.data.jpa.repository.JpaRepository;
import waktfolio.domain.entity.content.Content;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ContentRepository extends JpaRepository<Content, UUID>, ContentCustomRepository {
    List<Content> findByMemberId(UUID memberId);
    List<Content> findByIdIn(List<UUID> id);
}
