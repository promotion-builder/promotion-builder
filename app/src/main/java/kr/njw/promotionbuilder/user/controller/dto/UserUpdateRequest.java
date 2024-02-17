package kr.njw.promotionbuilder.user.controller.dto;


import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import jdk.jfr.Description;
import lombok.Data;

@Data
@Description("api user 등록용")
public class UserUpdateRequest {
    @Schema(description = "비밀번호", example = "password")
    @NotBlank(message = "[필수]비밀번호를 입력해 주세요.")
    @Size(min = 10, message = "비밀번호는 10자 이상 입력해 주세요.")
    private String password;
}
