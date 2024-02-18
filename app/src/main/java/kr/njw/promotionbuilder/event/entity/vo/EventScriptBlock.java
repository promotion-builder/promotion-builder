package kr.njw.promotionbuilder.event.entity.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.annotation.Transient;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "event_block")
@TypeAlias("EventScriptBlock")
@Data
@EqualsAndHashCode(callSuper = true)
public class EventScriptBlock extends EventBlock {
    @Schema(type = "string", example = "SCRIPT")
    @Transient
    private final BlockType blockType = BlockType.SCRIPT;

    @Schema(example = "alert('hi');")
    @NotNull(message = "must not be null")
    @Size(max = 1000, message = "size must be between 0 and 1000")
    private String script;
}
