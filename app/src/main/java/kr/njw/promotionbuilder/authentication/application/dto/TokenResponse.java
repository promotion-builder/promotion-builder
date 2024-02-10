package kr.njw.promotionbuilder.authentication.application.dto;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Getter
@Builder()
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class TokenResponse {

    @Schema(description = "access token")
    private String accessToken;

    @Schema(description = "refresh token")
    private String refreshToken;
}
