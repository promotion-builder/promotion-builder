package kr.njw.promotionbuilder.user.controller.dto;


import kr.njw.promotionbuilder.common.security.Role;
import lombok.*;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class UserDto {
    private String username;
    private Role role;
    private String refreshToken;
}
