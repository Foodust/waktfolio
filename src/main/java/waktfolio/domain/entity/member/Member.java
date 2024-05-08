package waktfolio.domain.entity.member;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.annotations.UuidGenerator;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import waktfolio.domain.BaseEntity;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Member{

    @Id
    @Column(updatable = false,nullable = false)
    private UUID id;

    @CreatedDate
    @CreationTimestamp
    @Column(nullable = false)
    private LocalDateTime createDate;

    @LastModifiedDate
    @UpdateTimestamp
    @Column(nullable = false)
    private LocalDateTime updateDate;

    private String loginId;
    private String password;
    private String name;
    private String profileImagePath;
    @Enumerated(value = EnumType.STRING)
    private MemberPermission memberPermission;
    private Boolean useYn;
}
