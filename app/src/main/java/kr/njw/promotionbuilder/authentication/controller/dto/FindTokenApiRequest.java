package kr.njw.promotionbuilder.authentication.controller.dto;


import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class FindTokenApiRequest {
    @Schema(description = "member id", example = "eddy")
    @NotBlank(message = "member id 가 비었습니다.")
    private String memberId;

    @Schema(description = "비밀번호")
    @NotNull(message = "비밀번호는 필수갑입니다.")
    private String password;
}
