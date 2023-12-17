package kr.njw.promotionbuilder.common.dto;


import kr.njw.promotionbuilder.common.security.Role;
import lombok.*;


@Getter
@Builder(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Login {
    private String memberId;
    private String password;
    private Role role;

    public static Login init(String memberId,
                             String password,
                             String role
    ) {
        return Login.builder()
                .memberId(memberId)
                .password(password)
                .role(Role.valueOf(role))
                .build();
    }
}
