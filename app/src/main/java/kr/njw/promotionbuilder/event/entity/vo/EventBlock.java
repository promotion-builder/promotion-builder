package kr.njw.promotionbuilder.event.entity.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.apache.commons.lang3.RandomStringUtils;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.EXISTING_PROPERTY, property = "blockType", visible = true)
@JsonSubTypes({
        @JsonSubTypes.Type(value = EventImageBlock.class, name = "IMAGE"),
        @JsonSubTypes.Type(value = EventLinkBlock.class, name = "LINK"),
        @JsonSubTypes.Type(value = EventScriptBlock.class, name = "SCRIPT"),
        @JsonSubTypes.Type(value = EventGiftBlock.class, name = "GIFT"),
})
@Data
public abstract class EventBlock {
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    protected String id;

    @Schema(description = "이벤트 통계 관리용 태그", example = "my event block")
    @Size(max = 300, message = "size must be between 0 and 300")
    protected String tag;

    @Schema(example = "https://d1y0pslxvt2ep5.cloudfront.net/event/content/content_83_1_20231016032640.png")
    @NotNull(message = "must not be null")
    @Size(max = 300, message = "size must be between 0 and 300")
    protected String image;

    public void issueId() {
        this.id = RandomStringUtils.random(32, "0123456789abcdef");
    }

    @NotNull(message = "must not be null")
    public BlockType getBlockType() {
        return null;
    }

    public enum BlockType {
        IMAGE,
        LINK,
        SCRIPT,
        GIFT
    }
}
