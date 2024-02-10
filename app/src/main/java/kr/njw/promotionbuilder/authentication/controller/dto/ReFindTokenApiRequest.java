package kr.njw.promotionbuilder.authentication.controller.dto;


import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class ReFindTokenApiRequest {
    @Schema(description = "access token", example = "")
    @NotBlank(message = "access token 을 입력해 주세요.")
    private String accessToken;

    @Schema(description = "refresh token", example = "")
    @NotBlank(message = "refresh token 을 입력해 주세요.")
    private String refreshToken;
}
