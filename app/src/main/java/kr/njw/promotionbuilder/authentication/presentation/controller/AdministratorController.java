package kr.njw.promotionbuilder.authentication.presentation.controller;


import kr.njw.promotionbuilder.authentication.services.AuthenticationService;
import kr.njw.promotionbuilder.common.controller.presentation.request.MemberLogin;
import kr.njw.promotionbuilder.common.dto.Login;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@Slf4j
@RestController
@RequestMapping("/v1/member")
@RequiredArgsConstructor
public class AdministratorController {
    private final AuthenticationService authenticationService;

    @PostMapping("/login")
    public void login(@RequestBody MemberLogin memberLogin) {
      log.info("member login = {}", memberLogin.getMemberId());
      authenticationService.login(Login.init(
              memberLogin.getMemberId(),memberLogin.getPassword()
      ));
    }
}
