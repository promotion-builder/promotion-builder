package kr.njw.promotionbuilder.user.controller.dto;


import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class CreateUserResponse {
    private Long id;
    private String secretKey;
}
