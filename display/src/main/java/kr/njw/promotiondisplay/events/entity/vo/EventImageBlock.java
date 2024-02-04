package kr.njw.promotiondisplay.events.entity.vo;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.annotation.Transient;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "event_block")
@TypeAlias("EventImageBlock")
@Data
@EqualsAndHashCode(callSuper = true)
public class EventImageBlock extends EventBlock {
    @Transient
    private final BlockType blockType = BlockType.IMAGE;
}
