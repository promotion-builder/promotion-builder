package kr.njw.promotiondisplay.event.entity.vo;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.EXISTING_PROPERTY, property = "blockType", visible = true)
@JsonSubTypes({
        @JsonSubTypes.Type(value = EventImageBlock.class, name = "IMAGE"),
        @JsonSubTypes.Type(value = EventLinkBlock.class, name = "LINK"),
        @JsonSubTypes.Type(value = EventScriptBlock.class, name = "SCRIPT"),
        @JsonSubTypes.Type(value = EventGiftBlock.class, name = "GIFT"),
})
@Data
public abstract class EventBlock {
    protected String id;
    protected String tag;
    protected String image;

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
