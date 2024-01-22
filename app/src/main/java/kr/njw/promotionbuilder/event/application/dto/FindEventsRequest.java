package kr.njw.promotionbuilder.event.application.dto;

import lombok.Data;

@Data
public class FindEventsRequest {
    private Long userId;
    private int page;
    private int pagingSize;
}
