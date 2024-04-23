package waktfolio.domain.repository.tag;

import org.springframework.data.jpa.repository.JpaRepository;
import waktfolio.domain.entity.content.Tag;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface TagRepository extends JpaRepository<Tag, UUID> {
    Optional<Tag> findByMemberIdAndName(UUID memberId, String name);
    List<Tag> findByMemberId(UUID memberId);
    Optional<Tag> findByIdAndMemberId(UUID tagId, UUID memberId);
}
