package waktfolio.domain.repository.view;

import org.springframework.data.jpa.repository.JpaRepository;
import waktfolio.domain.entity.view.MemberView;

import java.util.UUID;

public interface MemberViewRepository extends JpaRepository<MemberView, UUID> {
}
