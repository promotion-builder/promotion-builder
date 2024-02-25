package kr.njw.promotionbuilder.user.entity;

import jakarta.persistence.*;
import kr.njw.promotionbuilder.common.security.Role;
import kr.njw.promotionbuilder.user.entity.dto.UpdateUser;
import lombok.*;
import org.apache.commons.lang3.RandomStringUtils;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.time.LocalDateTime;

@Entity
@Table(name = "promotion_user")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
@ToString
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 100, nullable = false, unique = true)
    private String username;

    @Column(length = 100, nullable = false)
    private String password;

    @Column(length = 1000)
    private String refreshToken;

    @Column(length = 200)
    private String companyName;

    @Column(length = 1000)
    private String secretKey;

    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "ENUM('ACTIVE', 'DEACTIVATE') default 'ACTIVE'",nullable = false)
    private UserStatus status;

    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "ENUM('ADMIN', 'USER', 'GUEST') default 'USER'", nullable = false)
    private Role role;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    private LocalDateTime deletedAt;

    public void delete() {
        this.deletedAt = LocalDateTime.now();
    }

    public void updateUser(UpdateUser updateUser) {
        if (updateUser.getUsername() != null) this.username = updateUser.getUsername();
        if ( updateUser.getPassword() != null ) this.password = encryptPassword(updateUser.getPassword());
        if ( updateUser.getRefreshToken() != null ) this.refreshToken = updateUser.getRefreshToken();
        if ( updateUser.getCompanyName() != null ) this.companyName = updateUser.getCompanyName();
        if ( updateUser.getSecretKey() != null ) this.secretKey = updateUser.getSecretKey() ;

        if ( updateUser.getStatus() != null ) {
            this.status = updateUser.getStatus();
            if (updateUser.getStatus().equals(UserStatus.DELETED)) this.deletedAt = LocalDateTime.now();
        }

        if ( updateUser.getRole() != null ) this.role = updateUser.getRole();
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public enum UserStatus {
        ACTIVE,
        DEACTIVATE,
        DELETED
    }

    public static String encryptPassword(String password) {
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        return bCryptPasswordEncoder.encode(password);
    }

    public static String generateRandomHexString() {
        return RandomStringUtils.random(32, "0123456789abcdefghijklmnopqrstuvwxyz");
    }
}
