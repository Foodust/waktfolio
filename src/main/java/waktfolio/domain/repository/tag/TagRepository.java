package waktfolio.domain.repository.tag;

import org.springframework.data.jpa.repository.JpaRepository;
import waktfolio.domain.entity.content.Tag;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface TagRepository extends JpaRepository<Tag, UUID> {
    Optional<Tag> findByMemberIdAndName(UUID memberId, String name);
    List<Tag> findByMemberIdOrderByName(UUID memberId);
    List<Tag> findTop10ByOrderByCreateDateDesc();
    Optional<Tag> findByIdAndMemberId(UUID tagId, UUID memberId);
}
