package kr.njw.promotionbuilder.common.config;


import io.imagekit.sdk.ImageKit;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.web.client.RestTemplate;


@Slf4j
@RequiredArgsConstructor
@Configuration
public class ImageConfig {
    private final ImageKitProperties imageKitProperties;
}
