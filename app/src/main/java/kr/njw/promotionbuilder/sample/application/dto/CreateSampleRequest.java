package kr.njw.promotionbuilder.sample.application.dto;

import kr.njw.promotionbuilder.sample.entity.Sample;
import lombok.Data;

@Data
public class CreateSampleRequest {
    private Long userId;
    private String name;
    private Sample.SampleStatus status;
}
