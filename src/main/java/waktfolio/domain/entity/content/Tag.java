package waktfolio.domain.entity.content;

import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import waktfolio.domain.BaseEntity;

import java.util.UUID;

@SuperBuilder
@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Tag extends BaseEntity {
    private UUID memberId;
    @Setter
    private String name;
}
