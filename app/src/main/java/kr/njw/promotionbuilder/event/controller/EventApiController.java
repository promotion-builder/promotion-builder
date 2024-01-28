package kr.njw.promotionbuilder.event.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Positive;
import kr.njw.promotionbuilder.common.dto.BaseResponse;
import kr.njw.promotionbuilder.common.dto.BaseResponseStatus;
import kr.njw.promotionbuilder.common.exception.BaseException;
import kr.njw.promotionbuilder.event.application.EventService;
import kr.njw.promotionbuilder.event.application.dto.*;
import kr.njw.promotionbuilder.event.controller.dto.CreateEventApiRequest;
import kr.njw.promotionbuilder.event.controller.dto.EditEventApiRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/events")
public class EventApiController {
    private final EventService eventService;

    // @SecurityRequirement(name = "accessToken")
    @Operation(summary = "이벤트 목록 조회", description = "")
    @ApiResponses({
            @ApiResponse(responseCode = "200"),
            @ApiResponse(responseCode = "400", content = @Content()),
            @ApiResponse(responseCode = "403", content = @Content()),
    })
    @GetMapping("")
    public ResponseEntity<BaseResponse<FindEventsResponse>> findEvents(
            Principal principal,

            @Parameter(description = "페이지 번호")
            @Positive(message = "must be greater than 0")
            @RequestParam(value = "page", defaultValue = "1")
            Integer page,

            @Parameter(description = "페이징 사이즈 (최대 50)")
            @Min(value = 1, message = "must be greater than or equal to 1")
            @Max(value = 50, message = "must be less than or equal to 50")
            @RequestParam(value = "pagingSize", defaultValue = "20")
            Integer pagingSize
    ) {
        FindEventsRequest request = new FindEventsRequest();

        // TODO: 유저 아이디 설정
        request.setUserId(3L);
        request.setPage(page);
        request.setPagingSize(pagingSize);

        FindEventsResponse response = this.eventService.findEvents(request);

        return ResponseEntity.ok(new BaseResponse<>(response));
    }

    // @SecurityRequirement(name = "accessToken")
    @Operation(summary = "이벤트 상세 조회", description = "")
    @ApiResponses({
            @ApiResponse(responseCode = "200"),
            @ApiResponse(responseCode = "400", content = @Content()),
            @ApiResponse(responseCode = "403", content = @Content()),
            @ApiResponse(responseCode = "404", content = @Content())
    })
    @GetMapping("/{eventId}")
    public ResponseEntity<BaseResponse<FindEventResponse>> findEvent(
            Principal principal,

            @Parameter(description = "이벤트 ID", example = "65adadbbcc0c842e20eafd41")
            @PathVariable("eventId")
            String eventId
    ) {
        FindEventRequest request = new FindEventRequest();

        // TODO: 유저 아이디 설정
        request.setUserId(3L);
        request.setId(eventId);

        FindEventResponse response = this.eventService.findEvent(request).orElse(null);

        if (response == null) {
            throw new BaseException(BaseResponseStatus.NOT_FOUND);
        }

        return ResponseEntity.ok(new BaseResponse<>(response));
    }

    // @SecurityRequirement(name = "accessToken")
    @Operation(summary = "이벤트 생성", description = "")
    @ApiResponses({
            @ApiResponse(responseCode = "200"),
            @ApiResponse(responseCode = "403", content = @Content())
    })
    @PostMapping("")
    public ResponseEntity<BaseResponse<CreateEventResponse>> createEvent(Principal principal,
                                                                         @Valid @RequestBody CreateEventApiRequest apiRequest) {
        CreateEventRequest request = new CreateEventRequest();
        // TODO: 유저 아이디 설정
        request.setUserId(3L);
        request.setTitle(apiRequest.getTitle());
        request.setDescription(apiRequest.getDescription());
        request.setBannerImage(apiRequest.getBannerImage());
        request.setBlocks(apiRequest.getBlocks());
        request.setGrades(apiRequest.getGrades());
        request.setStartDateTime(apiRequest.getStartDateTime());
        request.setEndDateTime(apiRequest.getEndDateTime());

        return ResponseEntity.ok(new BaseResponse<>(this.eventService.createEvent(request)));
    }

    // @SecurityRequirement(name = "accessToken")
    @Operation(summary = "이벤트 수정", description = "")
    @ApiResponses({
            @ApiResponse(responseCode = "200"),
            @ApiResponse(responseCode = "403", content = @Content()),
            @ApiResponse(responseCode = "404", content = @Content())
    })
    @PutMapping("/{eventId}")
    public ResponseEntity<BaseResponse<EditEventResponse>> editEvent(
            Principal principal,

            @Parameter(description = "이벤트 ID", example = "65adadbbcc0c842e20eafd41")
            @PathVariable("eventId")
            String eventId,

            @Valid @RequestBody EditEventApiRequest apiRequest
    ) {
        EditEventRequest request = new EditEventRequest();
        request.setId(eventId);
        // TODO: 유저 아이디 설정
        request.setUserId(3L);
        request.setTitle(apiRequest.getTitle());
        request.setDescription(apiRequest.getDescription());
        request.setBannerImage(apiRequest.getBannerImage());
        request.setBlocks(apiRequest.getBlocks());
        request.setGrades(apiRequest.getGrades());
        request.setStartDateTime(apiRequest.getStartDateTime());
        request.setEndDateTime(apiRequest.getEndDateTime());

        return ResponseEntity.ok(new BaseResponse<>(this.eventService.editEvent(request)));
    }

    // @SecurityRequirement(name = "accessToken")
    @Operation(summary = "이벤트 삭제", description = "")
    @ApiResponses({
            @ApiResponse(responseCode = "200"),
    })
    @DeleteMapping("/{eventId}")
    public ResponseEntity<BaseResponse<Boolean>> deleteEvent(
            Principal principal,

            @Parameter(description = "이벤트 ID", example = "65adadbbcc0c842e20eafd41")
            @PathVariable("eventId")
            String eventId
    ) {
        DeleteEventRequest request = new DeleteEventRequest();
        request.setId(eventId);
        // TODO: 유저 아이디 설정
        request.setUserId(3L);

        this.eventService.deleteEvent(request);

        return ResponseEntity.ok(new BaseResponse<>(true));
    }
}
