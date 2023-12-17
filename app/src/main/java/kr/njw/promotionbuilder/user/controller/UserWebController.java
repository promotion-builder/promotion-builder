package kr.njw.promotionbuilder.user.controller;

import kr.njw.promotionbuilder.user.controller.dto.UserSignUp;
import kr.njw.promotionbuilder.user.services.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.SessionAttribute;

@Controller
@RequiredArgsConstructor
public class UserWebController {

    private final UserServiceImpl userService;

    @GetMapping("/login")
    public void login(@SessionAttribute(name ="userId", required = false) String userId) {}

    @PostMapping("/signup")
    public void signUp(UserSignUp signUp) {
        userService.signUp(signUp);
    }
}
