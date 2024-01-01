package kr.njw.promotionbuilder.user.controller;

import kr.njw.promotionbuilder.user.controller.dto.CreateUserResponse;
import kr.njw.promotionbuilder.user.controller.dto.UserSignUpRequest;
import kr.njw.promotionbuilder.user.services.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.net.URI;

@Controller
@RequiredArgsConstructor
@RequestMapping("/v1/user")
public class UserWebController {

    private final UserServiceImpl userService;

    @PostMapping(value = "/signUp", produces = "application/json; charset=UTF-8")
    public ResponseEntity<Object> signUp(@RequestBody UserSignUpRequest userSignUpRequest) {
        CreateUserResponse createUserResponse = userService.signUp(userSignUpRequest);
        return ResponseEntity.created(URI.create("/v1/user/"+createUserResponse.getId())).build();
    }
}
