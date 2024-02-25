package kr.njw.promotionbuilder.auth.application;

import kr.njw.promotionbuilder.auth.application.dto.TokenResponse;
import kr.njw.promotionbuilder.common.dto.LoginToken;

public interface TokenService {
    TokenResponse issue(LoginToken loginToken);

    TokenResponse rotate(String refreshToken);
}
