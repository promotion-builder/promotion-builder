
package kr.njw.promotionbuilder.common.config;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.stereotype.Component;


@Data
@ConfigurationProperties("imagekit")
@Component
@AllArgsConstructor
@NoArgsConstructor
public class ImageKitProperties {
    private String urlEndpoint;
    private String publicKey;
    private String privateKey;
    private String privateRestrictedKey;
    private String publicRestrictedKey;
}
