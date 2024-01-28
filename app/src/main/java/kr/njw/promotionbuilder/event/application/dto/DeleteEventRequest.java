package kr.njw.promotionbuilder.event.application.dto;

import lombok.Data;

@Data
public class DeleteEventRequest {
    private Long userId;
    private String id;
}
