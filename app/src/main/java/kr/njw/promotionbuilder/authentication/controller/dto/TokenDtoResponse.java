package kr.njw.promotionbuilder.authentication.controller.dto;


import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Getter
@Builder()
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TokenDtoResponse {
    @Schema(description = "grant type")
    private String grantType;

    @Schema(description = "access token")
    private String accessToken;

    @Schema(description = "refresh token")
    private String refreshToken;
}
