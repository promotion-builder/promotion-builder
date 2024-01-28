package kr.njw.promotionbuilder.event.application.dto;

import kr.njw.promotionbuilder.event.entity.vo.EventBlock;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
public class EditEventRequest {
    private String id;
    private Long userId;
    private String title;
    private String description;
    private String bannerImage;
    private List<EventBlock> blocks = new ArrayList<>();
    private List<String> grades;
    private LocalDateTime startDateTime;
    private LocalDateTime endDateTime;
}
