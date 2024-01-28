package kr.njw.promotiondisplay.sample.application.dto;

import kr.njw.promotiondisplay.common.dto.PageResponse;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
public class FindSamplesResponse extends PageResponse {
    private List<FindSampleResponse> samples;
}
