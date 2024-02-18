package kr.njw.promotiondisplay.event.entity.vo;

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
    @Transient
    private final BlockType blockType = BlockType.GIFT;
    private List<PrivacyField> privacyFields = new ArrayList<>();
    private List<Term> terms = new ArrayList<>();

    @Data
    public static class PrivacyField {
        private PrivacyFieldType type;
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
        private String title;
        private String content;
        private boolean required;
    }
}
