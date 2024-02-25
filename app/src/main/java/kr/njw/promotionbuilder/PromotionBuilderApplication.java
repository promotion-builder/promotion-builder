package kr.njw.promotionbuilder;

import kr.njw.promotionbuilder.user.entity.mapper.UserMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.data.mongodb.config.EnableMongoAuditing;

@SpringBootApplication
@EnableMongoAuditing
public class PromotionBuilderApplication {
    public static void main(String[] args) {
        SpringApplication.run(PromotionBuilderApplication.class, args);
    }
}
