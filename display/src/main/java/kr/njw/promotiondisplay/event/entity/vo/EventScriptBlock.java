package kr.njw.promotiondisplay.event.entity.vo;

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
    @Transient
    private final BlockType blockType = BlockType.SCRIPT;
    private String script;
}
