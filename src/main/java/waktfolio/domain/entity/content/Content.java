package waktfolio.domain.entity.content;

import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import waktfolio.domain.BaseEntity;

import java.util.UUID;

@Setter
@Entity
@Getter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class Content extends BaseEntity {
    private UUID memberId;
    private String name;
    private String description;
    private String thumbnailImagePath;
    private String objectPath;
    private String backGroundPath;
    private String backGroundColorCode;
    private String tagName;
    private String youtubeLink;
    private String cafeLink;
    private Boolean useYn;
}
