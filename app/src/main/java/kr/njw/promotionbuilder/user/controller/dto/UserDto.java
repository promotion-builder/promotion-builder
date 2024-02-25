package kr.njw.promotionbuilder.user.controller.dto;


import com.fasterxml.jackson.annotation.JsonInclude;
import kr.njw.promotionbuilder.common.security.Role;
import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserDto {
    private String username;
    private String password;
    private Role role;
    private String refreshToken;
}
