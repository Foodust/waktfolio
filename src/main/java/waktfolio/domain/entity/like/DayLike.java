package waktfolio.domain.entity.like;


import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import waktfolio.domain.BaseEntity;

import java.util.UUID;

@Entity
@Getter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class DayLike extends BaseEntity {
    private UUID contentId;
    private UUID contentGroupId;
    private Long likeCount;
}
