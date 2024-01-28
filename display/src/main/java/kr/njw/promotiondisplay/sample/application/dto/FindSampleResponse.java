package kr.njw.promotiondisplay.sample.application.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import kr.njw.promotiondisplay.sample.entity.Sample;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class FindSampleResponse {
    @Schema(description = "샘플 ID", example = "42")
    private Long id;

    @Schema(description = "샘플 이름", example = "샘플")
    private String name;

    @Schema(description = "샘플 상태", example = "ON")
    private Sample.SampleStatus status;

    @Schema(description = "샘플 생성일시", type = "string", example = "2022-10-17T13:01:53")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime createdAt;
}
