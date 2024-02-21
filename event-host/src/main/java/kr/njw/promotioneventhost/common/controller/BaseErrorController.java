package kr.njw.promotioneventhost.common.controller;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

// @Hidden
@Controller
public class BaseErrorController implements ErrorController {
    @RequestMapping("/error")
    public ResponseEntity<?> error(HttpServletResponse response) {
        return ResponseEntity.status(HttpStatus.valueOf(response.getStatus())).build();
    }
}
