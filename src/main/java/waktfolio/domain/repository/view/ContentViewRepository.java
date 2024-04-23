package waktfolio.domain.repository.view;

import org.springframework.data.jpa.repository.JpaRepository;
import waktfolio.domain.entity.view.ContentView;
import waktfolio.domain.entity.view.DayView;

import java.util.Optional;
import java.util.UUID;

public interface ContentViewRepository extends JpaRepository<ContentView, UUID> {
    Optional<ContentView> findByContentId(UUID contentId);
}
