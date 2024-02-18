package kr.njw.promotionbuilder.event.entity.vo;

import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Document(collection = "event_block_user_condition")
@TypeAlias("EventBlockUserCondition")
@Data
public class EventBlockUserCondition {
    @Schema(description = "true: 제한 있음, false: 제한 없음")
    @NotNull(message = "must not be null")
    private boolean enabled;

    private UserKey userKey;

    @Valid
    private UserConditionCheckApi additionalApi;

    @Schema(description = "유저별 총 응모 허용 횟수 (null: 무제한)", example = "10")
    @Min(value = 0, message = "must be greater than or equal to 0")
    private Long totalCountLimit;

    @Schema(description = "유저별 일일 응모 허용 횟수 (null: 무제한)", example = "5")
    @Min(value = 0, message = "must be greater than or equal to 0")
    private Long dailyCountLimit;

    @Schema(description = "ID: 아이디로 유저 구분, EMAIL: 이메일로 유저 구분, PHONE: 휴대폰으로 유저 구분")
    public enum UserKey {
        ID,
        EMAIL,
        PHONE
    }

    @Data
    public static class UserConditionCheckApi {
        @Schema(description = "업체가 구현한 유저 조건 체크 API URL", example = "http://localhost:8080/users/check")
        @NotNull(message = "must not be null")
        @Size(min = 1, max = 300, message = "size must be between 1 and 300")
        private String url;

        @ArraySchema(
                arraySchema = @Schema(description = "유저 조건 체크 API - 유저에게 필요한 Role 목록 (유저가 모든 Role을 소유해야 조건 만족)",
                        example = "[\"NEWBIE\", \"NOT_PURCHASED\"]")
        )
        @NotNull(message = "must not be null")
        @Size(max = 20, message = "size must be between 0 and 20")
        private List<
                @NotNull(message = "must not be null")
                @Size(min = 1, max = 100, message = "size must be between 1 and 100") String> roles = new ArrayList<>();
    }
}
