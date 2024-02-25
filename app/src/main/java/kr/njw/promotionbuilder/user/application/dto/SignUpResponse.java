package kr.njw.promotionbuilder.user.application.dto;


import lombok.*;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class SignUpResponse {
    private Long id;
}
