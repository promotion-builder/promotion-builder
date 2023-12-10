package kr.njw.promotionbuilder.user.services;

import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import kr.njw.promotionbuilder.common.security.Role;
import kr.njw.promotionbuilder.user.domain.entity.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class UserServiceImplTest {

    @Autowired
    private EntityManager entityManager;

    @Test
    @Transactional(rollbackOn = Exception.class)
    void 유저를생성할수있어야한다() {
        User user
          = User.builder()
                .username("eddyeddy")
                .password("aaaaa11111")
                .role(Role.USER)
                .status(User.UserStatus.ACTIVE)
                .build();

        entityManager.persist(user);

        Assertions.assertNotNull(user.getId());
    }
}
