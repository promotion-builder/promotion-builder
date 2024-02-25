package kr.njw.promotionbuilder.user.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/user/")
public class UserWebController {

    @GetMapping("/login")
    public String loginPage() {
        return "login/index";
    }

    @GetMapping("/main")
    public String mainPage() {
        return "login/main";
    }

    @GetMapping("/user")
    public String userPage() {
        return "login/user";
    }
}
