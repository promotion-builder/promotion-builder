package kr.njw.promotiondisplay.events.application.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import kr.njw.promotiondisplay.events.entity.vo.EventBlock;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
public class FindEventResponse {
    private String id;
    private String title;
    private String description;
    private String bannerImage;
    private List<EventBlock> blocks = new ArrayList<>();
    private List<String> grades;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime startDateTime;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime endDateTime;
}
