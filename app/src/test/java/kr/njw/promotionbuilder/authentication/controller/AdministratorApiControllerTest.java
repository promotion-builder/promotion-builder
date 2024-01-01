package kr.njw.promotionbuilder.authentication.controller;

import jakarta.transaction.Transactional;
import kr.njw.promotionbuilder.authentication.controller.dto.TokenDtoResponse;
import kr.njw.promotionbuilder.authentication.services.AuthenticationServiceImpl;
import kr.njw.promotionbuilder.common.dto.Login;
import kr.njw.promotionbuilder.common.security.Role;
import kr.njw.promotionbuilder.user.domain.entity.User;
import kr.njw.promotionbuilder.user.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.transaction.support.TransactionTemplate;

import java.util.Optional;


@SpringBootTest
class AdministratorApiControllerTest {
    private final String username = "eddyeddy";
    private final String password = "aaaaa11111";

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AuthenticationServiceImpl authenticationService;

    @Autowired
    private TransactionTemplate transactionTemplate;

    @BeforeEach
    void createUser() {
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        transactionTemplate.execute(status -> {
            try {
                userRepository.save(User.builder()
                        .username(username)
                        .password(bCryptPasswordEncoder.encode(password))
                        .role(Role.USER)
                        .status(User.UserStatus.ACTIVE)
                        .build());
                return null;
            } catch (Exception e) {
                System.out.println("dde : "+e.getMessage());
                return null;
            }
        });
    }

    @Test
    @Transactional(rollbackOn = Exception.class)
    void 테스트를진행하기전에user가생성되어야한다() {
        Optional<User> userByUsername = userRepository.findUserByUsername(username);
        Assertions.assertNotNull(userByUsername.get());
        Assertions.assertEquals(userByUsername.get().getUsername(), username);
    }

    @Test
    @Transactional(rollbackOn = Exception.class)
    void 토큰을발급받을수있어야한다() {
        TokenDtoResponse tokenDtoResponse = authenticationService.login(Login.init(
                username,
                password,
                Role.USER.getValue()
        ));
        Assertions.assertNotNull(tokenDtoResponse.getAccessToken());
    }
}
