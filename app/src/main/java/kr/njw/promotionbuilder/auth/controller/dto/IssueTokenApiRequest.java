package kr.njw.promotionbuilder.auth.controller.dto;


import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class IssueTokenApiRequest {
    @Schema(description = "등록 ID", example = "eddyid")
    @NotBlank(message = "아이디를 입력해주세요.")
    private String username;

    @Schema(description = "비밀번호", example = "password1234")
    @NotBlank(message = "비밀번호를 입력해주세요.")
    private String password;
}
