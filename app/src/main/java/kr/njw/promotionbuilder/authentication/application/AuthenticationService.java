package kr.njw.promotionbuilder.authentication.application;

import kr.njw.promotionbuilder.authentication.application.dto.TokenResponse;
import kr.njw.promotionbuilder.common.dto.Login;

public interface AuthenticationService {
    TokenResponse login(Login login);
    TokenResponse refreshToken(String accessToken, String refreshToken) throws InterruptedException;
}
