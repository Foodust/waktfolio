package waktfolio.domain.repository.like;

import org.springframework.data.jpa.repository.JpaRepository;
import waktfolio.domain.entity.like.DayLike;

import java.util.UUID;

public interface DayLikeRepository extends JpaRepository<DayLike, UUID>,DayLikeCustomRepository {
    DayLike findByMemberIdAndContentId(UUID memberId, UUID contentId);
    Long countByContentId(UUID contentId);
}
