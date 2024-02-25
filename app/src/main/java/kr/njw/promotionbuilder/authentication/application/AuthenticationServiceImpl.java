package kr.njw.promotionbuilder.authentication.application;

import kr.njw.promotionbuilder.authentication.application.dto.TokenResponse;
import kr.njw.promotionbuilder.common.dto.BaseResponseStatus;
import kr.njw.promotionbuilder.common.dto.Login;
import kr.njw.promotionbuilder.common.exception.BaseException;
import kr.njw.promotionbuilder.common.security.JwtAuthenticationProvider;
import kr.njw.promotionbuilder.common.security.Role;
import kr.njw.promotionbuilder.user.entity.User;
import kr.njw.promotionbuilder.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class AuthenticationServiceImpl implements AuthenticationService {
    private final UserRepository userRepository;
    private final JwtAuthenticationProvider jwtAuthenticationProvider;

    private final static BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public String login(Login login) {
        User user =
                userRepository.findUserByUsernameAndDeletedAtNull(login.getUsername())
                        .orElseThrow(() -> new BaseException(BaseResponseStatus.NOT_FOUND_USER));

        if (!passwordEncoder.matches(login.getPassword(), user.getPassword()))
            throw new BaseException(BaseResponseStatus.LOGIN_ERROR);

        if (user.getDeletedAt() != null && user.getDeletedAt().isBefore(LocalDateTime.now()))
            throw new BaseException(BaseResponseStatus.UNAUTHORIZED);

        List<Role> roles = new ArrayList<>();
        roles.add(user.getRole());

        String refreshToken = jwtAuthenticationProvider.createRefreshToken(user.getUsername(), roles);

        user.setRefreshToken(refreshToken);
        userRepository.saveAndFlush(user);

        return jwtAuthenticationProvider.createToken(user.getUsername(), roles);
    }

    public TokenResponse issue(Login login) {
        User user =
                userRepository.findUserByUsernameAndDeletedAtNull(login.getUsername())
                        .orElseThrow(() -> new BaseException(BaseResponseStatus.NOT_FOUND_USER));

        if (!passwordEncoder.matches(login.getPassword(), user.getPassword()))
            throw new BaseException(BaseResponseStatus.LOGIN_ERROR);

        if (user.getDeletedAt() != null && user.getDeletedAt().isBefore(LocalDateTime.now()))
            throw new BaseException(BaseResponseStatus.UNAUTHORIZED);

        List<Role> roles = new ArrayList<>();
        roles.add(user.getRole());

        String refreshToken = jwtAuthenticationProvider.createRefreshToken(user.getUsername(), roles);

        user.setRefreshToken(refreshToken);
        userRepository.saveAndFlush(user);

        return TokenResponse.builder()
                .refreshToken(refreshToken)
                .accessToken(jwtAuthenticationProvider.createToken(user.getUsername(), roles))
                .build();
    }

    public TokenResponse refreshToken(String accessToken, String refreshToken) throws InterruptedException {
        User user = userRepository.findUserByRefreshTokenAndDeletedAtNull(refreshToken)
                .orElseThrow(() -> new BaseException(BaseResponseStatus.RENEW_TOKEN_ERROR_BAD_REFRESH_TOKEN));

        List<Role> roles = new ArrayList<>();
        roles.add(user.getRole());

        if (needNewLogin(accessToken, refreshToken)) throw new BaseException(BaseResponseStatus.NEED_NEW_LOGIN);

        if (needReinssuranceAccessTokenOnly(accessToken)) {
            return TokenResponse.builder()
                    .refreshToken(refreshToken)
                    .accessToken(jwtAuthenticationProvider.createToken(user.getUsername(), roles))
                    .build();
        }

        return TokenResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }

    private boolean needReinssuranceAccessTokenOnly(String accessToken) {
        return !jwtAuthenticationProvider.validateToken(accessToken);
    }

    private boolean needNewLogin(String accessToken, String refreshToken) {
        return !jwtAuthenticationProvider.validateToken(accessToken) &&
                !jwtAuthenticationProvider.validateToken(refreshToken);
    }
}
