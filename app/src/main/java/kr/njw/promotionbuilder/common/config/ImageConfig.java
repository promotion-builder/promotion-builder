package kr.njw.promotionbuilder.common.config;


import io.imagekit.sdk.ImageKit;
import io.imagekit.sdk.config.Configuration;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.core.annotation.Order;



@Slf4j
@RequiredArgsConstructor
public class ImageConfig {
//    private final ImageKitProperties imageKitProperties;

//    @Bean
//    @Order(1)
//    public synchronized ImageKit imageKitRegister() {
//        ImageKit imageKit = ImageKit.getInstance();
//        Configuration config = new Configuration(imageKitProperties.getPublicKey(), imageKitProperties.getPrivateKey(),
//                imageKitProperties.getUrlEndpoint());
//        imageKit.setConfig(config);
//
//        log.debug("imagekit : {}", imageKit.getConfig());
//        return imageKit;
//    }
}
