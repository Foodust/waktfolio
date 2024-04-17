package waktfolio.domain.entity.content;

import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import waktfolio.domain.BaseEntity;

import java.util.List;
import java.util.UUID;

@Entity
@Getter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class ContentGroup extends BaseEntity {
    private UUID memberId;
    private String name;
    private String thumbnailImagePath;
    private List<String> tags;
    private Boolean useYn;
}