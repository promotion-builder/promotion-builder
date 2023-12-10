package kr.njw.promotionbuilder.image.controller;


import io.imagekit.sdk.exceptions.*;
import kr.njw.promotionbuilder.image.services.ImageServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;



@Slf4j
@RequestMapping(value = "/v1/image")
@RestController
@RequiredArgsConstructor
public class ImageApiController {

    private final ImageServiceImpl imageService;

    @PostMapping("/upload")
    public String uploadImage(@RequestParam("file") MultipartFile file) throws IOException, ForbiddenException,
            TooManyRequestsException, InternalServerException, UnauthorizedException, BadRequestException,
            UnknownException {

        return imageService.uploadImage(file);
    }
}
