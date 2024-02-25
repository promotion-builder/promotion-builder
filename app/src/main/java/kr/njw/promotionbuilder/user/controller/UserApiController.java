package kr.njw.promotionbuilder.user.controller;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import kr.njw.promotionbuilder.authentication.application.AuthenticationService;
import kr.njw.promotionbuilder.authentication.controller.dto.LoginApiRequest;
import kr.njw.promotionbuilder.common.dto.BaseResponse;
import kr.njw.promotionbuilder.common.dto.BaseResponseStatus;
import kr.njw.promotionbuilder.common.dto.Login;
import kr.njw.promotionbuilder.user.application.UserServiceImpl;
import kr.njw.promotionbuilder.user.controller.dto.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserApiController {
    private final UserServiceImpl userService;
    private final AuthenticationService authenticationService;

    @Operation(summary = "신규 가입", description = "")
    @ApiResponses({
            @ApiResponse(responseCode = "200"),
            @ApiResponse(responseCode = "302"),
            @ApiResponse(responseCode = "400", content = @Content()),
            @ApiResponse(responseCode = "404", content = @Content())
    })
    @PostMapping(value = "/signup", produces = "application/json; charset=UTF-8")
    public ResponseEntity<CreateUserResponse> signUp(
            @Valid @RequestBody UserSignUpRequest userSignUpRequest) {

        CreateUserResponse createUserResponse = userService.signUp(userSignUpRequest);

        return ResponseEntity.ok().body(createUserResponse);
    }

    @Operation(summary = "계정 정보 수정", description = "")
    @ApiResponses({
            @ApiResponse(responseCode = "200"),
            @ApiResponse(responseCode = "302"),
            @ApiResponse(responseCode = "400", content = @Content()),
            @ApiResponse(responseCode = "404", content = @Content())
    })
    @PutMapping(value = "/{username}", produces = "application/json; charset=UTF-8")
    public ResponseEntity<Object> updateUser(
            @PathVariable(value = "username") String username,
            @Valid @RequestBody UserUpdateRequest userUpdateRequest) {

        userService.updateUser(username, userUpdateRequest);
        return ResponseEntity.ok().body(new BaseResponse(BaseResponseStatus.SUCCESS));
    }

    @Operation(summary = "비밀번호 수정", description = "")
    @ApiResponses({
            @ApiResponse(responseCode = "200"),
            @ApiResponse(responseCode = "302"),
            @ApiResponse(responseCode = "400", content = @Content()),
            @ApiResponse(responseCode = "404", content = @Content())
    })
    @PutMapping(value = "/password/{username}", produces = "application/json; charset=UTF-8")
    public ResponseEntity<Object> updatePassword(
            @PathVariable(value = "username") String username,
            @Valid @RequestBody UserPasswordUpdateRequest userPasswordUpdateRequest) {

        userService.updateUserPassword(username, userPasswordUpdateRequest);
        return ResponseEntity.ok().body(new BaseResponse(BaseResponseStatus.SUCCESS));
    }

    @Operation(summary = "user id 수정", description = "")
    @ApiResponses({
            @ApiResponse(responseCode = "200"),
            @ApiResponse(responseCode = "302"),
            @ApiResponse(responseCode = "400", content = @Content()),
            @ApiResponse(responseCode = "404", content = @Content())
    })
    @PutMapping(value = "/username/{username}", produces = "application/json; charset=UTF-8")
    public ResponseEntity<Object> updateUsername(
            @PathVariable(value = "username") String username,
            @Valid @RequestBody UsernameUpdateRequest usernameUpdateRequest) {

        userService.updateUsername(username, usernameUpdateRequest);
        return ResponseEntity.ok().body(new BaseResponse(BaseResponseStatus.SUCCESS));
    }

    @Operation(summary = "계정 정보 호출", description = "")
    @ApiResponses({
            @ApiResponse(responseCode = "200"),
            @ApiResponse(responseCode = "302"),
            @ApiResponse(responseCode = "400", content = @Content()),
            @ApiResponse(responseCode = "404", content = @Content())
    })
    @GetMapping(value = "/{username}", produces = "application/json; charset=UTF-8")
    public ResponseEntity<Object> findByUsername(
            @PathVariable(value = "username") String username) {
        UserDto byUsername = userService.findByUsername(username);
        return ResponseEntity.ok().body(byUsername);
    }


    @Operation(summary = "유저 로그인", description = "")
    @ApiResponses({
            @ApiResponse(responseCode = "200"),
            @ApiResponse(responseCode = "302"),
            @ApiResponse(responseCode = "400", content = @Content()),
            @ApiResponse(responseCode = "404", content = @Content())
    })
    @PostMapping("/login")
    public ResponseEntity<Object> login(
            @Valid @RequestBody LoginApiRequest loginApiRequest, HttpServletResponse response) {
        String token =
                authenticationService.login(Login.init(
                        loginApiRequest.getUsername(),
                        loginApiRequest.getPassword()
                ));

        response.setHeader("Authorization", "Bearer " +token);
        return ResponseEntity.ok().build();
    }
}
