package waktfolio.domain.entity;

import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import waktfolio.domain.BaseEntity;

import java.util.List;

@Entity
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class Member extends BaseEntity {
    private String loginId;
    private String password;
    private String name;
    private String profileImagePath;
    private List<String> tags;
    private Boolean useYn;

}
