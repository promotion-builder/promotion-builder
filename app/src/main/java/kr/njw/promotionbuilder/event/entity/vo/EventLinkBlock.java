package kr.njw.promotionbuilder.event.entity.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.annotation.Transient;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "event_block")
@TypeAlias("EventLinkBlock")
@Data
@EqualsAndHashCode(callSuper = true)
public class EventLinkBlock extends EventBlock {
    @Schema(type = "string", example = "LINK")
    @Transient
    private final BlockType blockType = BlockType.LINK;

    @Schema(example = "https://github.com")
    @NotEmpty(message = "must not be empty")
    @Size(min = 1, max = 300, message = "size must be between 1 and 300")
    private String url;

    @Schema(example = "BLANK")
    @NotNull(message = "must not be null")
    private OpenType openType;

    public enum OpenType {
        SELF,
        BLANK
    }
}
