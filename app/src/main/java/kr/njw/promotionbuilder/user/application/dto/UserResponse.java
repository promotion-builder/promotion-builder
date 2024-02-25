package kr.njw.promotionbuilder.user.application.dto;

import kr.njw.promotionbuilder.common.security.Role;
import lombok.Data;

@Data
public class UserResponse {
    private Long id;
    private String username;
    private String companyName;
    private String secretKey;
    private Role role;
}
