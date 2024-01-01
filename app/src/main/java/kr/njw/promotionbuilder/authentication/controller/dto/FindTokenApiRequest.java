package kr.njw.promotionbuilder.authentication.controller.dto;


import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import kr.njw.promotionbuilder.common.security.Role;
import lombok.Data;
import lombok.Getter;

@Data
public class FindTokenApiRequest {
    @Schema(description = "member id", example = "eddy")
    @NotBlank(message = "member id 가 비었습니다.")
    private String memberId;

    @Schema(description = "비밀번호")
    @NotNull(message = "비밀번호는 필수갑입니다.")
    private String password;

    @Schema(description = "역할")
    @NotNull(message = "필수값이 비었습니다.")
    private Role role;
}
