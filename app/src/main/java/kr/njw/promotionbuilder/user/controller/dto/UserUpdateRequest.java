package kr.njw.promotionbuilder.user.controller.dto;


import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UserUpdateRequest {
    @Size(min = 10, message = "비밀번호는 10자 이상 입력해 주세요.")
    private String password;

    @Size(max = 200, message = "기업명으로 설정할 수 있는 최대 글자를 초과 하였습니다.")
    private String companyName;
}
