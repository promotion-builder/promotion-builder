package kr.njw.promotionbuilder.user.controller;

import jakarta.servlet.http.HttpSession;
import kr.njw.promotionbuilder.user.controller.presentation.UserSignUp;
import kr.njw.promotionbuilder.user.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/login")
    public String login(@SessionAttribute(name ="userId", required = false) String userId, Model model) {

        if (userId == null) return "login";

        return "home";
    }

    @PostMapping("/signup")
    public void signUp(UserSignUp signUp) {
        userService.signUp(signUp);
    }
}
