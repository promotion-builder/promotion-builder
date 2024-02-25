package kr.njw.promotionbuilder.auth.application.dto;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class TokenResponse {
    @Schema(description = "access token")
    private String accessToken;

    @Schema(description = "refresh token")
    private String refreshToken;
}
