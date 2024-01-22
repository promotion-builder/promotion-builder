package kr.njw.promotionbuilder.event.application.dto;

import kr.njw.promotionbuilder.common.dto.PageResponse;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
public class FindEventsResponse extends PageResponse {
    private List<FindEventResponse> events;
}
