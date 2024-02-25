package kr.njw.promotionbuilder.user.application.dto;


import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class SignUpRequest {
    @Schema(description = "등록 ID", example = "eddyid")
    @NotBlank(message = "아이디를 입력해주세요.")
    @Size(min = 5, max = 50, message = "아이디는 5자 이상 50자 이하로 입력해주세요.")
    private String username;

    @Schema(description = "비밀번호", example = "password1234")
    @NotBlank(message = "비밀번호를 입력해주세요.")
    @Size(min = 8, message = "비밀번호는 8자 이상으로 입력해주세요.")
    private String password;

    @Schema(description = "기업명", example = "홍콩반점")
    @NotBlank(message = "기업명을 입력해주세요.")
    @Size(max = 50, message = "기업명은 50자 이하로 입력해주세요.")
    private String companyName;
}
