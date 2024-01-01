package kr.njw.promotionbuilder.user.services;


import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import kr.njw.promotionbuilder.user.controller.dto.CreateUserResponse;
import kr.njw.promotionbuilder.user.controller.dto.UserSignUpRequest;
import kr.njw.promotionbuilder.user.domain.entity.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;


@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    @Override
    @Transactional(rollbackOn = Exception.class)
    public CreateUserResponse signUp(UserSignUpRequest userSignUpRequest) {
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        User user = User.builder()
                .username(userSignUpRequest.getUsername())
                .password(bCryptPasswordEncoder.encode(userSignUpRequest.getPassword()))
                .role(userSignUpRequest.getRole())
                .status(User.UserStatus.ACTIVE)
                .build();

        return CreateUserResponse.builder()
                .id(user.getId())
                .build();
    }
}
