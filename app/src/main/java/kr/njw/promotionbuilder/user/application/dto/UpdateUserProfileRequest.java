package kr.njw.promotionbuilder.user.application.dto;


import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UpdateUserProfileRequest {
    @Schema(description = "기업명", example = "홍콩반점")
    @NotBlank(message = "기업명을 입력해주세요.")
    @Size(max = 50, message = "기업명은 50자 이하로 입력해주세요.")
    private String companyName;
}
