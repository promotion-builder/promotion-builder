package kr.njw.promotionbuilder.common.dto;


import kr.njw.promotionbuilder.common.security.Role;
import lombok.*;


@Getter
@Builder(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Login {
    private String username;
    private String password;
    private Role role;

    public static Login init(String username,
                             String password
    ) {
        return Login.builder()
                .username(username)
                .password(password)
                .build();
    }
}
