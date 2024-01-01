package kr.njw.promotionbuilder.authentication.controller;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import kr.njw.promotionbuilder.authentication.controller.dto.TokenDtoResponse;
import kr.njw.promotionbuilder.authentication.services.AuthenticationService;
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
@RequestMapping("/v1/token")
@Validated
@RequiredArgsConstructor
public class AdministratorApiController {

    private final AuthenticationService authenticationService;

    @Operation(summary = "토큰 발급", description = "")
    @ApiResponses({
            @ApiResponse(responseCode = "200"),
            @ApiResponse(responseCode = "302"),
            @ApiResponse(responseCode = "400", content = @Content()),
            @ApiResponse(responseCode = "404", content = @Content())
    })
    @PostMapping("/issue")
    public ResponseEntity<BaseResponse<TokenDtoResponse>> issue(
            @RequestBody FindTokenApiRequest findTokenApiRequest) {
        return ResponseEntity.ok(
                new BaseResponse<>(authenticationService.login(Login.init(
                        findTokenApiRequest.getMemberId(),
                        findTokenApiRequest.getPassword(),
                        findTokenApiRequest.getRole().getValue()
                )))
        );
    }
}
