package kr.njw.promotionbuilder.user.controller.dto;


import kr.njw.promotionbuilder.common.security.Role;
import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class UserDto {
    private String username;
    private String password;
    private Role role;
    private String refreshToken;

    public void setUsernameId(String username) {
        this.username = username;
    }

    public void setPass(String password) {
        this.password = password;
    }

    public void setRoles(Role role) {
        this.role = role;
    }

    public void setReToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }
}
