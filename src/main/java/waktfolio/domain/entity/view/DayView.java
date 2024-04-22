package waktfolio.domain.entity.view;

import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import waktfolio.domain.BaseEntity;

import java.util.UUID;

@Getter
@Entity
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class DayView extends BaseEntity {
    private UUID contentId;
    private UUID memberId;
    private Long viewCount;
}
