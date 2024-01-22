package kr.njw.promotionbuilder.event.application.dto;

import lombok.Data;

@Data
public class FindEventRequest {
    private Long userId;
    private String id;
}
