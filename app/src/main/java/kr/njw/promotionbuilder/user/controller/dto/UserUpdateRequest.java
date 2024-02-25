package kr.njw.promotionbuilder.user.controller.dto;


import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UserUpdateRequest {
    @Schema(description = "기업명", example = "홍콩반점")
    @NotBlank(message = "[필수]기업명 입력해 주세요.")
    @Size(max = 200, message = "기업명으로 설정할 수 있는 최대 글자를 초과 하였습니다.")
    private String companyName;
}
