package kr.njw.promotionbuilder.auth.controller.dto;


import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class RotateTokenApiRequest {
    @Schema(description = "access token", example = "")
    @NotBlank(message = "access token을 입력해주세요.")
    private String accessToken;

    @Schema(description = "refresh token", example = "")
    @NotBlank(message = "refresh token을 입력해주세요.")
    private String refreshToken;
}
