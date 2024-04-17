package waktfolio.domain.repository.member;

import org.springframework.data.jpa.repository.JpaRepository;
import waktfolio.domain.entity.Member;

import java.util.Optional;
import java.util.UUID;

public interface MemberRepository extends JpaRepository<Member, UUID> {
    Optional<Member> findByLoginId(String loginId);
}
