package kr.njw.promotionbuilder.event.entity.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class EventBlockMessage {
    @Schema(example = "안내")
    @NotNull(message = "must not be null")
    @Size(max = 100, message = "size must be between 0 and 100")
    private String title;

    @Schema(example = "안내 메시지입니다.")
    @NotNull(message = "must not be null")
    @Size(max = 300, message = "size must be between 0 and 300")
    private String content;

    @Schema(example = "LINK_BLANK")
    @NotNull(message = "must not be null")
    private OnConfirmOperator onConfirm;

    @Schema(example = "http://localhost:8080")
    @Size(max = 300, message = "size must be between 0 and 300")
    private String onConfirmOperand;

    public enum OnConfirmOperator {
        NONE,
        LINK_SELF,
        LINK_BLANK,
        SCRIPT
    }
}
