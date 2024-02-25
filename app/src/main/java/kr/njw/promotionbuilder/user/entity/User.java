package kr.njw.promotionbuilder.user.entity;

import jakarta.persistence.*;
import kr.njw.promotionbuilder.common.security.Role;
import kr.njw.promotionbuilder.user.entity.dto.UserProfile;
import lombok.*;
import org.apache.commons.lang3.RandomStringUtils;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.security.crypto.password.PasswordEncoder;

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
    private String companyName;

    @Embedded
    private Password password;

    @Embedded
    private SecretKey secretKey;

    @Column(length = 500, unique = true)
    private String refreshToken;

    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "ENUM('ACTIVE', 'DEACTIVATE') default 'ACTIVE'", nullable = false)
    private UserStatus status;

    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "ENUM('ADMIN', 'USER') default 'USER'", nullable = false)
    private Role role;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    private LocalDateTime deletedAt;

    public static Password createPassword(String rawPassword, PasswordEncoder passwordEncoder) {
        Password password = new Password();
        password.password = passwordEncoder.encode(rawPassword);
        return password;
    }

    public static SecretKey generateSecretKey() {
        SecretKey secretKey = new SecretKey();
        secretKey.secretKey = RandomStringUtils.random(64, "0123456789abcdef");
        return secretKey;
    }

    public void delete() {
        this.deletedAt = LocalDateTime.now();
    }

    public void updateProfile(UserProfile profile) {
        if (profile.getCompanyName() != null) {
            this.companyName = profile.getCompanyName();
        }
    }

    public void changePassword(Password password) {
        this.password = password;
    }

    public void updateRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public enum UserStatus {
        ACTIVE,
        DEACTIVATE
    }

    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @EqualsAndHashCode
    @Embeddable
    public static class Password {
        @Column(length = 100, nullable = false)
        private String password;

        @Override
        public String toString() {
            return this.password;
        }
    }

    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @EqualsAndHashCode
    @Embeddable
    public static class SecretKey {
        @Column(length = 100, nullable = false)
        private String secretKey;

        @Override
        public String toString() {
            return this.secretKey;
        }
    }
}
