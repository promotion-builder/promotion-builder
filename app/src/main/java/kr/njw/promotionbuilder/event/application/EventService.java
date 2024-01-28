package kr.njw.promotionbuilder.event.application;

import kr.njw.promotionbuilder.event.application.dto.*;

import java.util.Optional;

public interface EventService {
    FindEventsResponse findEvents(FindEventsRequest request);

    Optional<FindEventResponse> findEvent(FindEventRequest request);

    CreateEventResponse createEvent(CreateEventRequest request);

    EditEventResponse editEvent(EditEventRequest request);

    void deleteEvent(DeleteEventRequest request);
}
