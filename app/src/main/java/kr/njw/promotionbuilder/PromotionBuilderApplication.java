package kr.njw.promotionbuilder;

import kr.njw.promotionbuilder.common.config.ImageConfig;
import kr.njw.promotionbuilder.common.config.ImageKitProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.data.mongodb.config.EnableMongoAuditing;

@SpringBootApplication
@EnableMongoAuditing
@EnableConfigurationProperties({ImageKitProperties.class})
public class PromotionBuilderApplication {
    public static void main(String[] args) {
        SpringApplication.run(PromotionBuilderApplication.class, args);
    }
}
