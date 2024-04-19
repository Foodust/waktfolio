package waktfolio.domain.repository.like;

import org.springframework.data.jpa.repository.JpaRepository;
import waktfolio.domain.entity.like.MemberLike;

import java.util.Optional;
import java.util.UUID;

public interface MemberLikeRepository extends JpaRepository<MemberLike, UUID> {
    MemberLike findByMemberIdAndContentId(UUID memberId, UUID contentId);
}
