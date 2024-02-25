package kr.njw.promotionbuilder.user.controller.dto;


import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;


@Getter
public class UserPasswordUpdateRequest {
    @Schema(description = "비밀번호", example = "password")
    @NotBlank(message = "[필수]비밀번호를 입력해 주세요.")
    @Size(min = 8, message = "비밀번호는 8자 이상 입력해 주세요.")
    private String password;

    public void setPassword(String password) {
        this.password = password;
    }
}
