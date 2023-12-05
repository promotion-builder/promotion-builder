package kr.njw.promotionbuilder.sample.application.dto;

import kr.njw.promotionbuilder.common.dto.PageResponse;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
public class FindSamplesResponse extends PageResponse {
    private List<FindSampleResponse> samples;
}
