package kr.njw.promotiondisplay.events.application;

import kr.njw.promotiondisplay.events.application.dto.FindEventRequest;
import kr.njw.promotiondisplay.events.application.dto.FindEventResponse;

import java.util.Optional;

public interface EventDisplayService {
    Optional<FindEventResponse> findEvent(FindEventRequest request);
}
