package kr.njw.promotionbuilder.authentication.services;

import kr.njw.promotionbuilder.authentication.controller.dto.TokenDtoResponse;
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

    public TokenDtoResponse login(Login login) {
        User user =
                userRepository.findUserByUsernameAndDeletedAtNull(login.getUsername())
                        .orElseThrow(() -> new BaseException(BaseResponseStatus.BAD_REQUEST));

        if (!passwordEncoder.matches(login.getPassword(), user.getPassword()))
            throw new BaseException(BaseResponseStatus.LOGIN_ERROR);

        if (user.getStatus().equals(User.UserStatus.DELETED) ||
                user.getStatus().equals(User.UserStatus.DEACTIVATE))
            throw new BaseException(BaseResponseStatus.UNAUTHORIZED);

        jwtAuthenticationProvider.createToken(user.getUsername(), List.of(Role.USER));

        String refreshToken = jwtAuthenticationProvider.createRefreshToken(user.getUsername(), List.of(Role.USER));

        user.setRefreshToken(refreshToken);
        userRepository.saveAndFlush(user);

        return TokenDtoResponse.builder()
                .refreshToken(refreshToken)
                .accessToken(jwtAuthenticationProvider.createToken(user.getUsername(), List.of(Role.USER)))
                .grantType("Bearer")
                .build();
    }

    public TokenDtoResponse refreshToken(String accessToken, String refreshToken) {

        User user = userRepository.findUserByRefreshTokenAndDeletedAtNull(refreshToken)
                .orElseThrow(() -> new BaseException(BaseResponseStatus.RENEW_TOKEN_ERROR_BAD_REFRESH_TOKEN));

        List<Role> roles = new ArrayList<>();
        roles.add(user.getRole());

        if (!needNewLogin(accessToken, refreshToken)) {
            return TokenDtoResponse.builder()
                    .accessToken(accessToken)
                    .refreshToken(refreshToken)
                    .build();
        } else if (needNewLogin(accessToken, refreshToken)
        ) {
            throw new BaseException(BaseResponseStatus.NEED_NEW_LOGIN);
        }

        if (!jwtAuthenticationProvider.validateToken(refreshToken)) {
            String newRefreshToken = jwtAuthenticationProvider.createRefreshToken(user.getUsername(), roles);
            user.setRefreshToken(newRefreshToken);
            userRepository.saveAndFlush(user);

            return TokenDtoResponse.builder()
                    .refreshToken(newRefreshToken)
                    .accessToken(accessToken)
                    .grantType("Bearer")
                    .build();
        }

        if (reIssueAccessToken(accessToken)) {
            return TokenDtoResponse.builder()
                    .refreshToken(refreshToken)
                    .accessToken(jwtAuthenticationProvider.createToken(user.getUsername(), roles))
                    .grantType("Bearer")
                    .build();
        }

        return null;
    }

    private boolean reIssueAccessToken(String accessToken) {
        return !jwtAuthenticationProvider.validateToken(accessToken);
    }

    private boolean needNewLogin(String accessToken, String refreshToken) {
        return !jwtAuthenticationProvider.validateToken(accessToken)
                && !jwtAuthenticationProvider.validateToken(refreshToken);
    }
}
