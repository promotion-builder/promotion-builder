package kr.njw.promotionbuilder.common.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class LoginToken {
    private final String username;
    private final String password;

    public static LoginToken create(String username, String password) {
        return new LoginToken(username, password);
    }
}
