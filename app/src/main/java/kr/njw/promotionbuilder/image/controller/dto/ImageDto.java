package kr.njw.promotionbuilder.image.controller.dto;

import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;

@Getter
public class ImageDto {

    private MultipartFile file;
}
