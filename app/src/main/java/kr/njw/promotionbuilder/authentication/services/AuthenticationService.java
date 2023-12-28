package kr.njw.promotionbuilder.authentication.services;

import kr.njw.promotionbuilder.authentication.controller.dto.MemberLoginApiRequest;
import kr.njw.promotionbuilder.authentication.controller.dto.TokenInfo;
import kr.njw.promotionbuilder.common.dto.Login;

public interface AuthenticationService {
    public TokenInfo login(Login login);
    public TokenInfo refreshToken(String refreshToken);
}
