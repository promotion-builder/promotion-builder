package kr.njw.promotionbuilder.image.controller;


import kr.njw.promotionbuilder.image.application.ImageProcessor;
import kr.njw.promotionbuilder.image.controller.dto.ImageDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;


@Slf4j
@RestController
@RequestMapping("/api/image")
@RequiredArgsConstructor
public class ImageController {
    private final ImageProcessor imageProcessor;

    @PostMapping("/upload")
    public ResponseEntity<Object> upload(@RequestBody ImageDto imageDto) throws IOException {
        imageProcessor.upload(imageDto);

        return ResponseEntity.ok().build();
    }
}
