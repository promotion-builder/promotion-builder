package kr.njw.promotiondisplay.sample.application.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class CreateSampleResponse {
    @Schema(description = "샘플 ID", example = "42")
    private Long id;
}
