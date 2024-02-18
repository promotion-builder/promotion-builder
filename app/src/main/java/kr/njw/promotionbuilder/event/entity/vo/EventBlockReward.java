package kr.njw.promotionbuilder.event.entity.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "event_block_reward")
@TypeAlias("EventBlockReward")
@Data
public class EventBlockReward {
    @Schema(example = "100% 할인 쿠폰")
    @NotNull(message = "must not be null")
    @Size(min = 1, max = 300, message = "size must be between 1 and 300")
    private String name;

    @Schema(description = "당첨 확률 (0.001% 단위)", example = "50000")
    @NotNull(message = "must not be null")
    @Min(value = 0, message = "must be greater than or equal to 0")
    private long percentage;

    @Schema(description = "총 당첨 허용 횟수 (null: 무제한)", example = "10")
    @Min(value = 0, message = "must be greater than or equal to 0")
    private Long totalCountLimit;

    @Schema(description = "일일 당첨 허용 횟수 (null: 무제한)", example = "5")
    @Min(value = 0, message = "must be greater than or equal to 0")
    private Long dailyCountLimit;

    @Valid
    @NotNull(message = "must not be null")
    private RewardIssuer issuer;

    @Valid
    @Schema(description = "당첨시 메시지")
    private EventBlockMessage message;

    @Data
    public static class RewardIssuer {
        @NotNull(message = "must not be null")
        private IssuerType type;

        @Schema(description = "업체가 구현한 리워드 지급 API URL", example = "http://localhost:8080/coupons")
        @Size(min = 1, max = 300, message = "size must be between 1 and 300")
        private String instantRewardApiUrl;

        @Schema(description = "리워드 지급 API - 리워드 ID", example = "100_DISCOUNT")
        @Size(min = 1, max = 300, message = "size must be between 1 and 300")
        private String instantRewardApiKey;

        @Schema(description = "리워드 지급 API - 리워드 수량", example = "1")
        private Long instantRewardApiAmount;

        @Schema(description = "INSTANT_API: 즉시지급 (업체가 구현한 리워드 지급 API 호출), " +
                "PUT: 나중에 지급 (프로모션빌더 당첨 현황에만 저장), " +
                "NONE: 꽝")
        public enum IssuerType {
            INSTANT_API,
            PUT,
            NONE
        }
    }
}
