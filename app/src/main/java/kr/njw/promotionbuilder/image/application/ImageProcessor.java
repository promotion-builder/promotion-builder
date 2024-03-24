package kr.njw.promotionbuilder.image.application;


import kr.njw.promotionbuilder.image.controller.dto.ImageDto;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

@Service
public class ImageProcessor {

    private static final int compressWidth = 500;

    public void upload(ImageDto imageDto) throws IOException {
        saveCompressedImageToFile(compressImage(imageDto.getFile()),
                "./src/main/java/kr/njw/promotionbuilder/image/sample/compressed.jpeg");
    }

    private static BufferedImage compressImage(MultipartFile file) throws IOException {
        // 이미지 파일 읽기
        BufferedImage image = ImageIO.read(convertMultipartFileToFile(file));

        // 이미지 크기 변경 (예: 가로 500px, 세로 자동 비율 유지)
        int targetWidth = compressWidth;
        int targetHeight = (int) ((double) image.getHeight() / image.getWidth() * targetWidth);
        BufferedImage resizedImage = new BufferedImage(targetWidth, targetHeight, BufferedImage.TYPE_INT_RGB);
        Graphics2D graphics = resizedImage.createGraphics();
        graphics.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        graphics.drawImage(image, 0, 0, targetWidth, targetHeight, null);
        graphics.dispose();

        return resizedImage;
    }

    public static File convertMultipartFileToFile(MultipartFile multipartFile) throws IOException {
        File file = new File(multipartFile.getOriginalFilename());
        FileOutputStream fos = new FileOutputStream(file);
        fos.write(multipartFile.getBytes());
        fos.close();
        return file;
    }

    private static void saveCompressedImageToFile(BufferedImage image, String outputFilePath) throws IOException {
        // 압축된 이미지를 파일로 저장
        ImageIO.write(image, "jpg", new File(outputFilePath));
    }
}
