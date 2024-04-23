package waktfolio.domain.repository.log;

import org.springframework.data.jpa.repository.JpaRepository;
import waktfolio.domain.entity.log.ViewLog;

import java.util.UUID;

public interface ViewLogRepository extends JpaRepository<ViewLog, UUID> {
}
