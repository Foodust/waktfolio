package waktfolio.domain.repository.content;

import org.springframework.data.jpa.repository.JpaRepository;
import waktfolio.domain.entity.content.ContentGroup;

import java.util.List;
import java.util.UUID;

public interface ContentGroupRepository extends JpaRepository<ContentGroup, UUID> {
    List<ContentGroup> findByIdIn(List<UUID> ids);
}
