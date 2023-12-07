package kr.njw.promotionbuilder.common.controller;

import io.swagger.v3.oas.annotations.Hidden;
import jakarta.servlet.http.HttpServletResponse;
import kr.njw.promotionbuilder.common.dto.BaseResponse;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Hidden
@RestController
public class BaseErrorController implements ErrorController {
    @RequestMapping("/error")
    public BaseResponse<?> error(HttpServletResponse response) {
        return new BaseResponse<>(response.getStatus(), "[BaseError] " + HttpStatus.valueOf(response.getStatus()).getReasonPhrase(), null);
    }
}
