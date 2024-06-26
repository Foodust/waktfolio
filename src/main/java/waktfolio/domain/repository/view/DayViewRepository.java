package waktfolio.domain.repository.view;

import org.springframework.data.jpa.repository.JpaRepository;
import waktfolio.domain.entity.view.DayView;

import java.util.Optional;
import java.util.UUID;

public interface DayViewRepository extends JpaRepository<DayView, UUID> ,DayViewCustomRepository{
    Optional<DayView>  findByContentId(UUID contentId);
}
