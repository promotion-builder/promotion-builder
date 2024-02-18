package kr.njw.promotiondisplay.event.entity;

import kr.njw.promotiondisplay.event.entity.vo.EventBlock;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;

@Document(collection = "event")
@TypeAlias("Event")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
@ToString
public class Event {
    @Id
    private String id;

    private String title;

    private String description;

    private String bannerImage;

    private List<EventBlock> blocks;

    private LocalDateTime startDateTime;

    private LocalDateTime endDateTime;
}
