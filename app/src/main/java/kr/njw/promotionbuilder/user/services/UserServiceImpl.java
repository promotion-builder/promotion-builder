package kr.njw.promotionbuilder.user.services;


import jakarta.transaction.Transactional;
import kr.njw.promotionbuilder.user.controller.dto.UserSignUp;
import kr.njw.promotionbuilder.user.domain.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    @Transactional(rollbackOn = Exception.class)
    public void signUp(UserSignUp userSignUp) {
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        User user = User.builder()
                .username(userSignUp.getUsername())
                .password(bCryptPasswordEncoder.encode(userSignUp.getPassword()))
                .build();
    }
}
