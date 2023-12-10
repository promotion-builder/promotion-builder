package kr.njw.promotionbuilder.image.services;


import io.imagekit.sdk.ImageKit;
import io.imagekit.sdk.config.Configuration;
import io.imagekit.sdk.exceptions.*;
import io.imagekit.sdk.models.FileCreateRequest;
import io.imagekit.sdk.models.results.Result;
import kr.njw.promotionbuilder.common.config.ImageKitProperties;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;



@Slf4j
@Service
@RequiredArgsConstructor
public class ImageServiceImpl implements ImageService {

    private final ImageKitProperties imageKitProperties;

    @Override
    public String uploadImage(MultipartFile file) throws IOException, ForbiddenException, TooManyRequestsException,
            InternalServerException, UnauthorizedException, BadRequestException, UnknownException {

        log.info("file : {}", file);
        ImageKit imageKit = ImageKit.getInstance();
        Configuration config = new Configuration(imageKitProperties.getPublicKey(), imageKitProperties.getPrivateKey(),
                imageKitProperties.getUrlEndpoint());
        imageKit.setConfig(config);

        try {
            Result upload = imageKit.upload(new FileCreateRequest(String.valueOf(file.getInputStream()), file.getName()));

            System.out.println("Uploaded Image URL: " + upload.getName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        return imageKit.toString();
    }

    @Override
    public void connectImageLibrary() {

    }

    public String hello() {
        return "";
    }
}
