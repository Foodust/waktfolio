package waktfolio.domain.entity.log;

import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import waktfolio.domain.BaseEntity;

import java.util.UUID;

@Getter
@SuperBuilder
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class ViewLog extends BaseEntity {
    private UUID contentId;
    private Long viewCount;
}
