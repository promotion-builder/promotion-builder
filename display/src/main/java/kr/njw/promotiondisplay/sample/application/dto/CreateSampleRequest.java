package kr.njw.promotiondisplay.sample.application.dto;

import kr.njw.promotiondisplay.sample.entity.Sample;
import lombok.Data;

@Data
public class CreateSampleRequest {
    private Long userId;
    private String name;
    private Sample.SampleStatus status;
}
