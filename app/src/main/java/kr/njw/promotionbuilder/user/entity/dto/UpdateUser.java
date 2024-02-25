package kr.njw.promotionbuilder.user.entity.dto;

import kr.njw.promotionbuilder.common.security.Role;
import kr.njw.promotionbuilder.user.entity.User;
import kr.njw.promotionbuilder.user.entity.mapper.UserMapper;
import lombok.*;


@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class UpdateUser {
    private String username;
    private String password;
    private String refreshToken;
    private String companyName;
    private String secretKey;
    private User.UserStatus status;
    private Role role;

    public void setPassword(String password) {
        this.password = password;
    }
    public void setUsername(String username) {
        this.username = username;
    }

    public void setCompanyName(String companyName) {this.companyName = companyName;}
}
