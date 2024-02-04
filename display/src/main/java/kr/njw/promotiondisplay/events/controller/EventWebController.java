package kr.njw.promotiondisplay.events.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@RequiredArgsConstructor
@Controller
@RequestMapping("/events")
public class EventWebController {
    @GetMapping(value = "/{userId}/{eventId}", produces = "application/javascript; charset=utf-8")
    public String event(@PathVariable("userId") String userId,
                        @PathVariable("eventId") String eventId,
                        Model model) {
        model.addAttribute("title", "Hello, world!");
        model.addAttribute("userId", userId);
        model.addAttribute("eventId", eventId);

        return "events/detail.js";
    }
}
