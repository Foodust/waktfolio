package waktfolio.domain.repository.like;

import org.springframework.data.jpa.repository.JpaRepository;
import waktfolio.domain.entity.like.DayLike;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface DayLikeRepository extends JpaRepository<DayLike, UUID>,DayLikeCustomRepository {
    List<DayLike> findByMemberId(UUID memberId);
    Optional<DayLike> findByMemberIdAndContentId(UUID memberId, UUID contentId);
    Long countByContentId(UUID contentId);
}
