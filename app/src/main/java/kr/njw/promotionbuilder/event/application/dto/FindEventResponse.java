package kr.njw.promotionbuilder.event.application.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Schema;
import kr.njw.promotionbuilder.event.entity.vo.EventBlock;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class FindEventResponse {
    @Schema(description = "이벤트 ID", example = "65adadbbcc0c842e20eafd41")
    private String id;

    @Schema(description = "유저 ID", example = "3")
    private Long userId;

    @Schema(description = "이벤트명", example = "회원가입 이벤트")
    private String title;

    @Schema(description = "소개글", example = "회원가입하면 풍성한 혜택이!")
    private String description;

    @Schema(description = "배너 이미지", example = "https://d1y0pslxvt2ep5.cloudfront.net/event/banner/banner_83_20231016032640.png")
    private String bannerImage;

    @ArraySchema(
            arraySchema = @Schema(description = "이벤트 페이지 블럭 목록 (이벤트 목록 조회 시에는 null)", nullable = true)
    )
    private List<EventBlock> blocks;

    @ArraySchema(
            arraySchema = @Schema(description = "이벤트 대상 회원 등급 목록 (등급 제한 없으면 null)", example = "null", nullable = true)
    )
    private List<String> grades;

    @Schema(description = "이벤트 시작일시", type = "string", example = "2024-01-01T00:00:00")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime startDateTime;

    @Schema(description = "이벤트 종료일시", type = "string", example = "2024-01-31T23:59:59")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime endDateTime;

    @Schema(description = "생성일시", type = "string", example = "2022-10-17T13:01:53")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime createdAt;

    @Schema(description = "수정일시", type = "string", example = "2022-10-17T13:01:53")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime updatedAt;
}
