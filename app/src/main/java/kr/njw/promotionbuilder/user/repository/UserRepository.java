package kr.njw.promotionbuilder.user.repository;


import jakarta.persistence.LockModeType;
import kr.njw.promotionbuilder.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findByIdAndDeletedAtNull(Long id);

    Optional<User> findByUsername(String username);

    Optional<User> findByUsernameAndDeletedAtNull(String username);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    Optional<User> findForUpdateByRefreshTokenAndDeletedAtNull(String refreshToken);
}
