package kr.njw.promotionbuilder.sample.entity;

import jakarta.persistence.*;
import lombok.*;
import org.apache.commons.lang3.RandomStringUtils;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "sample")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
@ToString
public class Sample {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long user;

    @Column(length = 45, nullable = false)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, columnDefinition = "enum('ON', 'OFF')")
    private SampleStatus status;

    @CreationTimestamp
    @Column
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column
    private LocalDateTime updatedAt;

    @Column
    private LocalDateTime deletedAt;

    public enum SampleStatus {
        ON,
        OFF
    }

    public void delete() {
        this.deletedAt = LocalDateTime.now();
    }

    public void randomizeName() {
        this.name = RandomStringUtils.randomAlphanumeric(32);
    }

    public void toggleStatus() {
        if (this.status == null || this.status == SampleStatus.OFF) {
            this.status = SampleStatus.ON;
        } else {
            this.status = SampleStatus.OFF;
        }
    }
}
