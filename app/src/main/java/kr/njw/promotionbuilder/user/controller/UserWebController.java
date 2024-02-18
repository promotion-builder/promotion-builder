package kr.njw.promotionbuilder.user.controller;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import kr.njw.promotionbuilder.authentication.application.AuthenticationService;
import kr.njw.promotionbuilder.authentication.controller.dto.LoginApiRequest;
import kr.njw.promotionbuilder.common.dto.Login;
import kr.njw.promotionbuilder.user.controller.dto.CreateUserResponse;
import kr.njw.promotionbuilder.user.controller.dto.UserDto;
import kr.njw.promotionbuilder.user.controller.dto.UserSignUpRequest;
import kr.njw.promotionbuilder.user.controller.dto.UserUpdateRequest;
import kr.njw.promotionbuilder.user.services.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class UserWebController {

    private final UserServiceImpl userService;

    private final AuthenticationService authenticationService;

    @PostMapping("/login")
    public ResponseEntity<?> login(
            @Valid @RequestBody LoginApiRequest loginApiRequest, HttpServletResponse response) {
        String token = authenticationService.login(Login.init(
                loginApiRequest.getUsername(),
                loginApiRequest.getPassword()
        ));

        response.setHeader("Authorization", "Bearer " +token);

        return ResponseEntity.ok().build();
    }

    @PostMapping(value = "/", produces = "application/json; charset=UTF-8")
    public ResponseEntity<CreateUserResponse> signUp(@RequestBody UserSignUpRequest userSignUpRequest) {

        CreateUserResponse createUserResponse = userService.signUp(userSignUpRequest);

        return ResponseEntity.ok().body(createUserResponse);
    }

    @PutMapping(value = "/{userId}", produces = "application/json; charset=UTF-8")
    public ResponseEntity<Object> updateUser(@PathVariable String userId,
                                                     @RequestBody UserUpdateRequest userUpdateRequest) {

        if (userId != null) {
            userService.updateUser(userId, userUpdateRequest);
        }

        return ResponseEntity.ok().build();
    }

    @GetMapping(value = "/{username}", produces = "application/json; charset=UTF-8")
    public ResponseEntity<Object> findByUsername(@PathVariable(value = "username") String username) {
        UserDto byUsername = userService.findByUsername(username);
        return ResponseEntity.ok().body(byUsername);
    }
}
