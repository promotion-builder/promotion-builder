package kr.njw.promotionbuilder.user.controller.dto;


import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import jdk.jfr.Description;
import lombok.Data;

@Data
@Description("api user 등록용")
public class UserSignUpRequest {

    @Schema(description = "등록 ID", example = "eddy")
    @NotBlank(message = "[필수]아이디를 입력해 주세요.")
    @Size(min = 5)
    private String username;

    @Schema(description = "비밀번호", example = "password")
    @NotBlank(message = "[필수]비밀번호를 입력해 주세요.")
    @Size(min = 10, message = "비밀번호는 10자 이상 입력해 주세요.")
    private String password;

    @Schema(description = "기업명", example = "홍콩반점")
    @NotBlank(message = "[필수]기업명 입력해 주세요.")
    @Size(max = 200, message = "기업명으로 설정할 수 있는 최대 글자를 초과 하였습니다.")
    private String companyName;
}
