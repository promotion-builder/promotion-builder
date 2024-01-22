package kr.njw.promotionbuilder.event.entity;

import jakarta.persistence.Id;
import kr.njw.promotionbuilder.event.entity.vo.EventBlock;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.ArrayList;
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

    private Long userId;

    private String title;

    private String description;

    private String bannerImage;

    private List<EventBlock> blocks = new ArrayList<>();

    private List<String> grades;

    private LocalDateTime startDateTime;

    private LocalDateTime endDateTime;

    @CreatedDate
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;

    private LocalDateTime deletedAt;

    public void delete() {
        this.deletedAt = LocalDateTime.now();
    }
}
