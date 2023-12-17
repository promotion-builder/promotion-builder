package kr.njw.promotionbuilder.authentication.controller.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class MemberLogin {
    private String memberId;
    private String password;
}
