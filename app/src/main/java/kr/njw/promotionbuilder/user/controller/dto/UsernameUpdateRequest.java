package kr.njw.promotionbuilder.user.controller.dto;


import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class UsernameUpdateRequest {
    @Schema(description = "등록 ID", example = "eddy")
    @NotBlank(message = "[필수]아이디를 입력해 주세요.")
    @Size(min = 4, max = 15)
    private String username;

    public void setUsername(String username) {
        this.username = username;
    }
}
