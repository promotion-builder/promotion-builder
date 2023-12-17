package kr.njw.promotionbuilder.common.controller.presentation.request;


import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class MemberLogin {
    private String memberId;
    private String password;
}
