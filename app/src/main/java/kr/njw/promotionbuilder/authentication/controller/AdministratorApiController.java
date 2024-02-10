package kr.njw.promotionbuilder.authentication.controller;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import kr.njw.promotionbuilder.authentication.controller.dto.ReFindTokenApiRequest;
import kr.njw.promotionbuilder.authentication.application.dto.TokenResponse;
import kr.njw.promotionbuilder.authentication.application.AuthenticationService;
import kr.njw.promotionbuilder.authentication.controller.dto.FindTokenApiRequest;
import kr.njw.promotionbuilder.common.dto.BaseResponse;
import kr.njw.promotionbuilder.common.dto.Login;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


@Slf4j
@RestController
@RequestMapping("/api/token")
@Validated
@RequiredArgsConstructor
public class AdministratorApiController {

    private AuthenticationService authenticationService;

    @Operation(summary = "로그인", description = "")
    @ApiResponses({
            @ApiResponse(responseCode = "200"),
            @ApiResponse(responseCode = "302"),
            @ApiResponse(responseCode = "400", content = @Content()),
            @ApiResponse(responseCode = "404", content = @Content())
    })
    @PostMapping("/login")
    public ResponseEntity<BaseResponse<TokenResponse>> issue(
            @Valid @RequestBody FindTokenApiRequest findTokenApiRequest) {
        return ResponseEntity.ok(
                new BaseResponse<>(authenticationService.login(Login.init(
                        findTokenApiRequest.getMemberId(),
                        findTokenApiRequest.getPassword()
                )))
        );
    }

    @Operation(summary = "토큰 재발급", description = "")
    @ApiResponses({
            @ApiResponse(responseCode = "200"),
            @ApiResponse(responseCode = "302"),
            @ApiResponse(responseCode = "400", content = @Content()),
            @ApiResponse(responseCode = "404", content = @Content())
    })
    @PostMapping("/reinssuance")
    public ResponseEntity<BaseResponse<TokenResponse>> issue(
            @RequestBody ReFindTokenApiRequest reFindTokenApiRequest) throws InterruptedException {
        return ResponseEntity.ok(
                new BaseResponse<>(
                        authenticationService.refreshToken(
                                reFindTokenApiRequest.getAccessToken(),
                                reFindTokenApiRequest.getRefreshToken()
                        )
                )
        );
    }
}
