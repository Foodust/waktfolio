package waktfolio.domain.entity;

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
public class Content extends BaseEntity {
    private UUID memberId;

    private UUID groupId;
    private String thumbnailImagePath;
    private String name;
    private String objectPath;
    private Integer likes;
    private Integer views;
    private List<String> tags;
}
