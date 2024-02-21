package kr.njw.promotioneventhost;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.config.EnableMongoAuditing;

@SpringBootApplication
@EnableMongoAuditing
public class PromotionEventHostApplication {
    public static void main(String[] args) {
        SpringApplication.run(PromotionEventHostApplication.class, args);
    }
}
