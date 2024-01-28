package kr.njw.promotiondisplay.sample.application.dto;

import lombok.Data;

@Data
public class FindSamplesRequest {
    private int page;
    private int pagingSize;
}
