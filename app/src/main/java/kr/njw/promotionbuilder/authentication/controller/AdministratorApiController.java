package kr.njw.promotionbuilder.authentication.controller;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import kr.njw.promotionbuilder.authentication.services.AuthenticationService;
import kr.njw.promotionbuilder.authentication.services.AuthenticationServiceImpl;
import kr.njw.promotionbuilder.authentication.controller.dto.MemberLoginApiRequest;
import kr.njw.promotionbuilder.common.dto.Login;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


@Slf4j
@RestController
@RequestMapping("/v1/member")
@Validated
@RequiredArgsConstructor
public class AdministratorApiController {

    private AuthenticationService authenticationService;

    public AdministratorApiController(
            AuthenticationServiceImpl authenticationService
    ) {
        this.authenticationService = authenticationService;
    }

    @Operation(summary = "로그인", description = """
            로그인 후 토큰 발급
            """)
    @ApiResponses({
            @ApiResponse(responseCode = "200"),
            @ApiResponse(responseCode = "302"),
            @ApiResponse(responseCode = "400", content = @Content()),
            @ApiResponse(responseCode = "404", content = @Content())
    })
    @PostMapping("/login")
    public void login(@RequestBody MemberLoginApiRequest memberLogin) {
      authenticationService.login(Login.init(
              memberLogin.getMemberId(),
              memberLogin.getPassword(),
              memberLogin.getRole().getValue()
      ));
    }
}
