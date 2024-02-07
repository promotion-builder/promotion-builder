package kr.njw.promotiondisplay.event.application;

import kr.njw.promotiondisplay.event.application.dto.FindEventRequest;
import kr.njw.promotiondisplay.event.application.dto.FindEventResponse;

import java.util.Optional;

public interface EventDisplayService {
    Optional<FindEventResponse> findEvent(FindEventRequest request);
}
