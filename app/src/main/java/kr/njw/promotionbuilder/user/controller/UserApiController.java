package kr.njw.promotionbuilder.user.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import kr.njw.promotionbuilder.common.dto.BaseResponse;
import kr.njw.promotionbuilder.common.utils.AuthUtils;
import kr.njw.promotionbuilder.user.application.UserService;
import kr.njw.promotionbuilder.user.application.dto.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/users")
public class UserApiController {
    private final UserService userService;

    @Operation(summary = "신규 가입", description = "")
    @ApiResponses({
            @ApiResponse(responseCode = "200"),
            @ApiResponse(responseCode = "400", content = @Content()),
    })
    @PostMapping("")
    public ResponseEntity<BaseResponse<SignUpResponse>> signUp(@Valid @RequestBody SignUpRequest request) {
        SignUpResponse response = this.userService.signUp(request);
        return ResponseEntity.ok().body(new BaseResponse<>(response));
    }

    @SecurityRequirement(name = "accessToken")
    @Operation(summary = "내 계정정보 수정", description = "")
    @ApiResponses({
            @ApiResponse(responseCode = "200"),
            @ApiResponse(responseCode = "400", content = @Content()),
            @ApiResponse(responseCode = "401", content = @Content()),
            @ApiResponse(responseCode = "403", content = @Content()),
            @ApiResponse(responseCode = "404", content = @Content()),
    })
    @PatchMapping("/me")
    public ResponseEntity<BaseResponse<Boolean>> updateUserProfile(
            Principal principal,
            @Valid @RequestBody UpdateUserProfileRequest request
    ) {
        this.userService.updateUserProfile(AuthUtils.getUserId(principal), request);
        return ResponseEntity.ok().body(new BaseResponse<>(true));
    }

    @SecurityRequirement(name = "accessToken")
    @Operation(summary = "내 비밀번호 수정", description = "")
    @ApiResponses({
            @ApiResponse(responseCode = "200"),
            @ApiResponse(responseCode = "400", content = @Content()),
            @ApiResponse(responseCode = "401", content = @Content()),
            @ApiResponse(responseCode = "403", content = @Content()),
            @ApiResponse(responseCode = "404", content = @Content()),
    })
    @PatchMapping("/me/password")
    public ResponseEntity<BaseResponse<Boolean>> changePassword(
            Principal principal,
            @Valid @RequestBody ChangePasswordRequest request
    ) {
        this.userService.changeUserPassword(AuthUtils.getUserId(principal), request);
        return ResponseEntity.ok().body(new BaseResponse<>(true));
    }

    @SecurityRequirement(name = "accessToken")
    @Operation(summary = "내 계정정보 조회", description = "")
    @ApiResponses({
            @ApiResponse(responseCode = "200"),
            @ApiResponse(responseCode = "400", content = @Content()),
            @ApiResponse(responseCode = "401", content = @Content()),
            @ApiResponse(responseCode = "403", content = @Content()),
            @ApiResponse(responseCode = "404", content = @Content()),
    })
    @GetMapping("/me")
    public ResponseEntity<BaseResponse<UserResponse>> findById(Principal principal) {
        UserResponse response = this.userService.findByUserId(AuthUtils.getUserId(principal));
        return ResponseEntity.ok().body(new BaseResponse<>(response));
    }

    @Operation(summary = "아이디 중복체크", description = """
            result가 true면 아이디가 이미 존재함. false면 아이디가 존재하지 않음
            """)
    @ApiResponses({
            @ApiResponse(responseCode = "200"),
            @ApiResponse(responseCode = "400", content = @Content()),
    })
    @GetMapping("/username")
    public ResponseEntity<BaseResponse<Boolean>> findByUsername(@Parameter(example = "eddyid") @RequestParam(name = "q") String username) {
        return ResponseEntity.ok().body(new BaseResponse<>(this.userService.isUsernameUsed(username)));
    }
}
