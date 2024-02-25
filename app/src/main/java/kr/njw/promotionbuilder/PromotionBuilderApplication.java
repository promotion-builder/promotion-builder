package kr.njw.promotionbuilder;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.config.EnableMongoAuditing;

@SpringBootApplication
@EnableMongoAuditing
public class PromotionBuilderApplication {
    public static void main(String[] args) {
        SpringApplication.run(PromotionBuilderApplication.class, args);
    }
}
