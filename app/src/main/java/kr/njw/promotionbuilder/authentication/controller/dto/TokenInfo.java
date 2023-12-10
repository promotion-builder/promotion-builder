package kr.njw.promotionbuilder.authentication.controller.dto;


import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

@Getter
@Builder(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TokenInfo {
    private String grantType;
    private String accessToken;
    private String refreshToken;

    public static TokenInfo init(String grantType, String accessToken, String refreshToken) {
        return TokenInfo.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .grantType(grantType)
                .build();
    }
}
