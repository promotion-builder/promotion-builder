package kr.njw.promotiondisplay;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.config.EnableMongoAuditing;

@SpringBootApplication
@EnableMongoAuditing
public class PromotionDisplayApplication {
    public static void main(String[] args) {
        SpringApplication.run(PromotionDisplayApplication.class, args);
    }
}
