package kr.njw.promotionbuilder.image.controller;


import io.imagekit.sdk.ImageKit;
import io.imagekit.sdk.config.Configuration;
import io.imagekit.sdk.exceptions.*;
import io.imagekit.sdk.models.FileCreateRequest;
import io.imagekit.sdk.models.results.Result;
import io.imagekit.sdk.tasks.RestClient;
import kr.njw.promotionbuilder.common.config.ImageKitProperties;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;


@Slf4j
@RestController
@RequestMapping("/api/image")
@RequiredArgsConstructor
public class ImageController {
    private final ImageKitProperties imageKitProperties;
    private final RestTemplate restTemplate;
    @GetMapping("")
    public void getImage() throws IOException {
        log.info("images transtering...");

        String imagePath = "src/main/java/kr/njw/promotionbuilder/image/sample/sample.txt";

        try (
                FileInputStream fileInputStream = new FileInputStream(imagePath);
                FileOutputStream fos = new FileOutputStream(imagePath);
                Writer writer = new OutputStreamWriter(fos, "UTF-8")
        ) {
            StringBuilder txt = new StringBuilder();
            int data;
            while ((data = fileInputStream.read()) != -1) {
                txt.append((char) data);
            }

            System.out.printf("txt : "+txt.toString());
            writer.write(txt.toString()+"안녕");
            System.out.println("Content has been written to " + imagePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @PostMapping("/upload2")
    public void upload2() {

        String imagePath = "src/main/java/kr/njw/promotionbuilder/image/sample/IMG_1533.jpg";

        File imageFile = new File(imagePath);

        byte[] imageData = new byte[(int) imageFile.length()];

        FileCreateRequest fileCreateRequest = new FileCreateRequest(
                imageData, "IMG_1533.jpg"
        );
        RestClient restClient = null;
        try {
            Configuration configuration = new Configuration(
                    imageKitProperties.getPublicRestrictedKey(),
                    imageKitProperties.getPrivateRestrictedKey(),
                    imageKitProperties.getUrlEndpoint()
            );
            ImageKit imageKit = ImageKit.getInstance();
            imageKit.setConfig(configuration);
            restClient = new RestClient(imageKit);
            Result uploadResult = restClient.upload(fileCreateRequest);
            log.info("upload result : {}", uploadResult);
        } catch( Exception e) {
            log.error("error : {}", e.getStackTrace());
        }
    }

    @PostMapping("/upload")
    public void upload() throws IOException {
        log.info("images uploading...");

        ImageKit imageKit = ImageKit.getInstance();

        Configuration configuration = new Configuration(
                imageKitProperties.getPublicRestrictedKey(),
                imageKitProperties.getPrivateRestrictedKey(),
                imageKitProperties.getUrlEndpoint()
        );

        imageKit.setConfig(configuration);

        FileCreateRequest fileCreateRequest =
                new FileCreateRequest("https://ik.imagekit.io/ktest92/spring.svg?updatedAt=1706451579160",
                        "IMG_1533.jpg");
        List<String> responseFields=new ArrayList<>();
        responseFields.add("thumbnail");
        responseFields.add("tags");
        responseFields.add("customCoordinates");
        fileCreateRequest.setResponseFields(responseFields);
        List<String> tags=new ArrayList<>();
        tags.add("Software");
        tags.add("Developer");
        tags.add("Engineer");
        fileCreateRequest.setTags(tags);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<FileCreateRequest> requestEntity =
                new HttpEntity<>(fileCreateRequest, headers);

        String apiUrl = "https://ik.imagekit.io/ktest92/IMG_1533.jpg";

        // POST 요청을 보냅니다.
        ResponseEntity<Result> response =
                restTemplate.postForEntity(apiUrl, requestEntity, Result.class);

        log.info("response : {}",response);
        // 응답 결과를 확인합니다.
        if (response.getStatusCode().is2xxSuccessful()) {
            Result result = response.getBody();
            System.out.println("Image uploaded successfully. Result: " + result);
        } else {
            System.err.println("Failed to upload image. Status code: " + response.getStatusCodeValue());
        }
    }

    private static byte[] readBytesFromFile(String filePath) throws IOException {
        Path path = Paths.get(filePath);
        return Files.readAllBytes(path);
    }
}
