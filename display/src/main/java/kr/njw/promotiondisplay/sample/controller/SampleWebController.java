package kr.njw.promotiondisplay.sample.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequiredArgsConstructor
@Controller
@RequestMapping("/sample")
public class SampleWebController {
    @GetMapping("")
    public String sample(Model model) {
        model.addAttribute("title", "Hello, world!");

        return "sample/index";
    }
}
