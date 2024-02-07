package kr.njw.promotiondisplay.event.entity.vo;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.EXISTING_PROPERTY, property = "blockType", visible = true)
@JsonSubTypes({
        @JsonSubTypes.Type(value = EventImageBlock.class, name = "IMAGE"),
        @JsonSubTypes.Type(value = EventLinkBlock.class, name = "LINK"),
        @JsonSubTypes.Type(value = EventScriptBlock.class, name = "SCRIPT"),
})
@Data
public abstract class EventBlock {
    @NotEmpty(message = "must not be empty")
    @Size(min = 1, max = 300, message = "size must be between 1 and 300")
    protected String image;

    public BlockType getBlockType() {
        return null;
    }

    public enum BlockType {
        IMAGE,
        LINK,
        SCRIPT
    }
}
