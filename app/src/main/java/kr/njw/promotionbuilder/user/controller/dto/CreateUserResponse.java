package kr.njw.promotionbuilder.user.controller.dto;


import lombok.*;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class CreateUserResponse {
    private Long id;
}
