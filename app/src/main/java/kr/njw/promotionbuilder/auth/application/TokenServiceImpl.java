package kr.njw.promotionbuilder.auth.application;

import kr.njw.promotionbuilder.auth.application.dto.TokenResponse;
import kr.njw.promotionbuilder.common.dto.BaseResponseStatus;
import kr.njw.promotionbuilder.common.dto.LoginToken;
import kr.njw.promotionbuilder.common.exception.BaseException;
import kr.njw.promotionbuilder.common.security.JwtAuthenticationProvider;
import kr.njw.promotionbuilder.common.security.Role;
import kr.njw.promotionbuilder.user.entity.User;
import kr.njw.promotionbuilder.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Slf4j
@RequiredArgsConstructor
@Service
public class TokenServiceImpl implements TokenService {
    private final UserRepository userRepository;
    private final JwtAuthenticationProvider jwtAuthenticationProvider;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public TokenResponse issue(LoginToken loginToken) {
        User user = this.userRepository.findByUsernameAndDeletedAtNull(loginToken.getUsername())
                .orElseThrow(() -> new BaseException(BaseResponseStatus.UNAUTHORIZED_LOGIN_TOKEN));

        if (!this.passwordEncoder.matches(loginToken.getPassword(), user.getPassword().toString())) {
            throw new BaseException(BaseResponseStatus.UNAUTHORIZED_LOGIN_TOKEN);
        }

        return this.issueTokenPair(user);
    }

    @Transactional
    public TokenResponse rotate(String refreshToken) {
        boolean isRefreshTokenValid = this.jwtAuthenticationProvider.validateToken(refreshToken);

        if (!isRefreshTokenValid) {
            throw new BaseException(BaseResponseStatus.INVALID_REFRESH_TOKEN);
        }

        User user = this.userRepository.findForUpdateByRefreshTokenAndDeletedAtNull(refreshToken).orElse(null);

        if (user == null) {
            log.warn("회원번호 {}의 리프레시 토큰을 재사용하려는 시도가 감지되었습니다. 토큰 탈취에 주의하세요.", this.jwtAuthenticationProvider.getUserId(refreshToken).orElse(null));
            throw new BaseException(BaseResponseStatus.USED_REFRESH_TOKEN);
        }

        return this.issueTokenPair(user);
    }

    private TokenResponse issueTokenPair(User user) {
        List<Role> roles = List.of(user.getRole());

        String accessToken = this.jwtAuthenticationProvider.createAccessToken(user.getId(), roles);
        String refreshToken = this.jwtAuthenticationProvider.createRefreshToken(user.getId(), roles);

        user.updateRefreshToken(refreshToken);
        this.userRepository.save(user);

        TokenResponse response = new TokenResponse();
        response.setAccessToken(accessToken);
        response.setRefreshToken(refreshToken);
        return response;
    }
}
