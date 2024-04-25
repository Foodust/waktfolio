package waktfolio.domain.entity.member;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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
    @Enumerated(value = EnumType.STRING)
    private MemberPermission memberPermission;
    private Boolean useYn;
}
