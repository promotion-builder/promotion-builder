package kr.njw.promotionbuilder.auth.controller;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.headers.Header;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import kr.njw.promotionbuilder.auth.application.TokenService;
import kr.njw.promotionbuilder.auth.application.dto.TokenResponse;
import kr.njw.promotionbuilder.auth.controller.dto.IssueTokenApiRequest;
import kr.njw.promotionbuilder.common.dto.BaseResponse;
import kr.njw.promotionbuilder.common.dto.LoginToken;
import kr.njw.promotionbuilder.common.utils.AuthUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


@Slf4j
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/auth")
public class AuthApiController {
    private final TokenService tokenService;

    @Operation(summary = "토큰 발급", description = """
            아이디와 비밀번호로 새로운 엑세스 토큰과 새로운 리프레시 토큰 발급

            새로운 리프레시 토큰은 쿠키에 세팅됨
            """)
    @ApiResponses({
            @ApiResponse(responseCode = "200",
                    headers = {
                            @Header(name = "Set-Cookie", description = "리프레시 토큰 (HttpOnly 세션쿠키)",
                                    schema = @Schema(example = "promotion_builder_refresh_token=Lwj8TE5Lr97EnRRQYbyP1Zu4h4tBht4i; Path=/; Secure; HttpOnly; SameSite=None")
                            )
                    }),
            @ApiResponse(responseCode = "400", content = @Content()),
            @ApiResponse(responseCode = "401", content = @Content()),
            @ApiResponse(responseCode = "404", content = @Content()),
    })
    @PostMapping("/tokens")
    public ResponseEntity<BaseResponse<TokenResponse>> issue(@Valid @RequestBody IssueTokenApiRequest request, HttpServletResponse httpServletResponse) {
        TokenResponse tokenResponse = this.tokenService.issue(LoginToken.create(request.getUsername(), request.getPassword()));

        AuthUtils.setRefreshTokenCookie(httpServletResponse, tokenResponse.getRefreshToken());
        return ResponseEntity.ok(new BaseResponse<>(tokenResponse));
    }

    @Operation(summary = "토큰 갱신", description = """
            기존 리프레시 토큰(쿠키에서 자동으로 가져옴)을 받아 새로운 엑세스 토큰과 새로운 리프레시 토큰 발급. 새로운 리프레시 토큰은 쿠키에 세팅됨

            단, 기존 리프레시 토큰이 만료된 상태면 갱신이 불가능함

            또한, 한번 사용한 리프레시 토큰은 폐기되어 재사용할 수 없음
            """)
    @ApiResponses({
            @ApiResponse(responseCode = "200",
                    headers = {
                            @Header(name = "Set-Cookie", description = "리프레시 토큰 (HttpOnly 세션쿠키)",
                                    schema = @Schema(example = "promotion_builder_refresh_token=Lwj8TE5Lr97EnRRQYbyP1Zu4h4tBht4i; Path=/; Secure; HttpOnly; SameSite=None")
                            )
                    }),
            @ApiResponse(responseCode = "400", content = @Content()),
            @ApiResponse(responseCode = "401", content = @Content()),
            @ApiResponse(responseCode = "404", content = @Content()),
    })
    @PatchMapping("/tokens")
    public ResponseEntity<BaseResponse<TokenResponse>> refresh(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        TokenResponse tokenResponse = this.tokenService.rotate(AuthUtils.getRefreshToken(httpServletRequest));

        AuthUtils.setRefreshTokenCookie(httpServletResponse, tokenResponse.getRefreshToken());
        return ResponseEntity.ok(new BaseResponse<>(tokenResponse));
    }
}
