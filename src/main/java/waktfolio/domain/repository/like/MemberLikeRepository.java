package waktfolio.domain.repository.like;

import org.springframework.data.jpa.repository.JpaRepository;
import waktfolio.domain.entity.like.MemberLike;

import java.util.UUID;

public interface MemberLikeRepository extends JpaRepository<MemberLike, UUID> {
    Long countByContentId(UUID contentId);
    Long countByMemberId(UUID memberId);
    MemberLike findByMemberIdAndContentId(UUID memberId, UUID contentId);
}
