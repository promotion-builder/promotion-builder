package kr.njw.promotionbuilder.event.entity.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.annotation.Transient;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Document(collection = "event_block")
@TypeAlias("EventGiftBlock")
@Data
@EqualsAndHashCode(callSuper = true)
public class EventGiftBlock extends EventBlock {
    @Schema(type = "string", example = "GIFT")
    @Transient
    private final BlockType blockType = BlockType.GIFT;

    @Valid
    @NotNull(message = "must not be null")
    private EventBlockUserCondition userCondition;

    @Valid
    @NotNull(message = "must not be null")
    @Size(min = 1, max = 100, message = "size must be between 1 and 100")
    private List<@NotNull(message = "must not be null") EventBlockReward> rewards = new ArrayList<>();

    @Valid
    @NotNull(message = "must not be null")
    @Size(max = 10, message = "size must be between 0 and 10")
    private List<@NotNull(message = "must not be null") PrivacyField> privacyFields = new ArrayList<>();

    @Valid
    @NotNull(message = "must not be null")
    @Size(max = 10, message = "size must be between 0 and 10")
    private List<@NotNull(message = "must not be null") Term> terms = new ArrayList<>();

    @Valid
    @NotNull(message = "must not be null")
    private EventBlockErrorMessageContainer errorMessage;

    @Data
    public static class PrivacyField {
        @NotNull(message = "must not be null")
        private PrivacyFieldType type;

        @NotNull(message = "must not be null")
        private boolean required;

        public enum PrivacyFieldType {
            NAME,
            GENDER,
            BIRTH,
            PHONE,
            ADDRESS,
            EMAIL
        }
    }

    @Data
    public static class Term {
        @Schema(example = "개인정보 수집 및 이용 동의")
        @NotNull(message = "must not be null")
        @Size(min = 1, max = 300, message = "size must be between 1 and 300")
        private String title;

        @Schema(example = "우리 회사는 개인정보를 수집하여 이용합니다.")
        @NotNull(message = "must not be null")
        @Size(min = 1, max = 10000, message = "size must be between 1 and 10000")
        private String content;

        @NotNull(message = "must not be null")
        private boolean required;
    }
}
