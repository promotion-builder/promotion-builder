package kr.njw.promotionbuilder.event.application.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class CreateEventResponse {
    @Schema(description = "이벤트 ID", example = "65adadbbcc0c842e20eafd41")
    private String id;
}
