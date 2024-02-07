package kr.njw.promotiondisplay.event.controller;

import kr.njw.promotiondisplay.event.application.EventDisplayService;
import kr.njw.promotiondisplay.event.application.dto.FindEventRequest;
import kr.njw.promotiondisplay.event.application.dto.FindEventResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.ModelAndView;

@Slf4j
@RequiredArgsConstructor
@Controller
@RequestMapping("/events")
public class EventWebController {
    private final EventDisplayService eventDisplayService;

    @GetMapping(value = "/{eventId}.js", produces = "application/javascript; charset=utf-8")
    public ModelAndView event(@PathVariable("eventId") String eventId) {
        FindEventRequest request = new FindEventRequest();
        request.setId(eventId);
        FindEventResponse response = this.eventDisplayService.findEvent(request).orElse(null);

        ModelAndView modelAndView = new ModelAndView();

        if (response == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

        modelAndView.setViewName("events/detail.js");
        modelAndView.addObject("eventId", eventId);
        modelAndView.addObject("event", response);

        return modelAndView;
    }

    @GetMapping(value = "/{eventId}")
    public ModelAndView eventPage(@PathVariable("eventId") String eventId) {
        ModelAndView modelAndView = new ModelAndView();

        modelAndView.setViewName("events/detail-page");
        modelAndView.addObject("eventId", eventId);

        return modelAndView;
    }
}
