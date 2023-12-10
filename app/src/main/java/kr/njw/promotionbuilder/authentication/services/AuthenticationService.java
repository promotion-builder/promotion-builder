package kr.njw.promotionbuilder.authentication.services;

import kr.njw.promotionbuilder.authentication.controller.dto.TokenDtoResponse;
import kr.njw.promotionbuilder.authentication.controller.dto.TokenInfo;
import kr.njw.promotionbuilder.common.dto.Login;

public interface AuthenticationService {
    public TokenDtoResponse login(Login login);
    public TokenDtoResponse refreshToken(String accessToken, String refreshToken);
}
