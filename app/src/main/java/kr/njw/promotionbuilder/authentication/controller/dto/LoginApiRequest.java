package kr.njw.promotionbuilder.authentication.controller.dto;


import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class LoginApiRequest {
    @Schema(description = "username", example = "eddy")
    @NotBlank(message = "username이 비었습니다.")
    private String username;

    @Schema(description = "비밀번호")
    @NotNull(message = "비밀번호는 필수갑입니다.")
    private String password;
}
