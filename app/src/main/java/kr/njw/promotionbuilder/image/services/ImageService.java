package kr.njw.promotionbuilder.image.services;


import io.imagekit.sdk.exceptions.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface ImageService {

    String uploadImage(MultipartFile file) throws IOException, ForbiddenException, TooManyRequestsException, InternalServerException, UnauthorizedException, BadRequestException, UnknownException;

    void connectImageLibrary();


}
