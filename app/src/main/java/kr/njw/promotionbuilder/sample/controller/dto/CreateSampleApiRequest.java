package kr.njw.promotionbuilder.sample.controller.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import kr.njw.promotionbuilder.sample.entity.Sample;
import lombok.Data;

@Data
public class CreateSampleApiRequest {
    @Schema(description = "샘플 이름", example = "샘플")
    @NotBlank(message = "must not be blank")
    @Size(min = 2, max = 10, message = "size must be between 2 and 10")
    private String name;

    @Schema(description = "샘플 상태", example = "ON")
    @NotNull(message = "must not be null")
    private Sample.SampleStatus status;
}
