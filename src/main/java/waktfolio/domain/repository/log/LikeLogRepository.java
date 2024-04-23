package waktfolio.domain.repository.log;

import org.springframework.data.jpa.repository.JpaRepository;
import waktfolio.domain.entity.log.LikeLog;

import java.util.UUID;

public interface LikeLogRepository extends JpaRepository<LikeLog, UUID> {
}
