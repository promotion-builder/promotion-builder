package kr.njw.promotionbuilder.sample.application.dto;

import lombok.Data;

@Data
public class FindSamplesRequest {
    private int page;
    private int pagingSize;
}
