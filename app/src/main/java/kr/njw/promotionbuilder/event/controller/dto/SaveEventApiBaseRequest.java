package kr.njw.promotionbuilder.event.controller.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import kr.njw.promotionbuilder.event.entity.vo.EventBlock;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
public abstract class SaveEventApiBaseRequest {
    @Schema(example = "회원가입 이벤트")
    @NotNull(message = "must not be null")
    @Size(min = 1, max = 100, message = "size must be between 1 and 100")
    private String title;

    @Schema(example = "회원가입하면 풍성한 혜택이!")
    @NotNull(message = "must not be null")
    @Size(max = 300, message = "size must be between 0 and 300")
    private String description;

    @Schema(example = "https://d1y0pslxvt2ep5.cloudfront.net/event/banner/banner_83_20231016032640.png")
    @NotNull(message = "must not be null")
    @Size(max = 300, message = "size must be between 0 and 300")
    private String bannerImage;

    @Valid
    @NotNull(message = "must not be null")
    @Size(max = 20, message = "size must be between 0 and 20")
    private List<@NotNull(message = "must not be null") EventBlock> blocks = new ArrayList<>();

    @ArraySchema(
            arraySchema = @Schema(description = "등급 제한 없으면 null", example = "null", nullable = true)
    )
    @Size(max = 20, message = "size must be between 0 and 20")
    private List<
            @NotNull(message = "must not be null")
            @Size(min = 1, max = 100, message = "size must be between 1 and 100") String> grades = new ArrayList<>();

    @Schema(type = "string", example = "2024-05-21T14:08:45")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    @NotNull(message = "must not be null")
    private LocalDateTime startDateTime;

    @Schema(type = "string", example = "2024-07-19T10:21:25")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    @NotNull(message = "must not be null")
    private LocalDateTime endDateTime;
}
