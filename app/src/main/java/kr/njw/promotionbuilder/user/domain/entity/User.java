package kr.njw.promotionbuilder.user.domain.entity;

import jakarta.persistence.*;
import kr.njw.promotionbuilder.user.enums.UserStatus;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "promotion_user")
@Setter
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(columnDefinition = "int", name = "pu_index")
    private Integer index;

    @Column(columnDefinition = "varchar(100) default ''", nullable = false, name = "pu_id")
    private String id;

    @Column(columnDefinition = "varchar(100) default ''", nullable = false, name = "pu_password")
    private String password;

    @Column(columnDefinition = "enum('ACTIVE', 'DEACTIVATE', 'DELETED') default 'ACTIVE'",nullable = false,
    name = "pu_status")
    private UserStatus status;

    @CreationTimestamp
    private LocalDateTime registerTime;

    @UpdateTimestamp
    private LocalDateTime updateTime;
}
