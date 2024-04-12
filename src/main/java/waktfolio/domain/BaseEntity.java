package waktfolio.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.annotations.UuidGenerator;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Getter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class BaseEntity {

    @Id
    @UuidGenerator
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
}
