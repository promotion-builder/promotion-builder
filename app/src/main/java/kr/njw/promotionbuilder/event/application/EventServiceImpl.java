package kr.njw.promotionbuilder.event.application;

import kr.njw.promotionbuilder.event.application.dto.*;
import kr.njw.promotionbuilder.event.entity.Event;
import kr.njw.promotionbuilder.event.repository.EventRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;
import java.util.Optional;

@RequiredArgsConstructor
@Transactional
@Service
public class EventServiceImpl implements EventService {
    private final EventRepository eventRepository;

    @Override
    public FindEventsResponse findEvents(FindEventsRequest request) {
        final int MAX_PAGING_SIZE = 1000;

        Page<Event> eventPage = this.eventRepository.findByUserIdAndDeletedAtNullOrderByCreatedAtDesc(
                request.getUserId(),
                PageRequest.of(request.getPage() - 1, Math.min(request.getPagingSize(), MAX_PAGING_SIZE))
        );

        FindEventsResponse response = new FindEventsResponse();
        response.setPage(eventPage.getNumber() + 1);
        response.setPageSize(eventPage.getNumberOfElements());
        response.setPagingSize(eventPage.getSize());
        response.setTotalPage(eventPage.getTotalPages());
        response.setTotalSize(eventPage.getTotalElements());
        response.setEvents(eventPage.getContent().stream().map(event -> {
            FindEventResponse eventResponse = new FindEventResponse();
            eventResponse.setId(event.getId());
            eventResponse.setUserId(event.getUserId());
            eventResponse.setTitle(event.getTitle());
            eventResponse.setDescription(event.getDescription());
            eventResponse.setBannerImage(event.getBannerImage());
            eventResponse.setBlocks(null);
            eventResponse.setGrades(event.getGrades());
            eventResponse.setStartDateTime(event.getStartDateTime());
            eventResponse.setEndDateTime(event.getEndDateTime());
            eventResponse.setCreatedAt(event.getCreatedAt());
            eventResponse.setUpdatedAt(event.getUpdatedAt());
            return eventResponse;
        }).toList());

        return response;
    }

    @Override
    public Optional<FindEventResponse> findEvent(FindEventRequest request) {
        Event event = this.eventRepository.findByIdAndDeletedAtNull(request.getId()).orElse(null);

        if (event == null || !Objects.equals(event.getUserId(), request.getUserId())) {
            return Optional.empty();
        }

        FindEventResponse response = new FindEventResponse();
        response.setId(event.getId());
        response.setUserId(event.getUserId());
        response.setTitle(event.getTitle());
        response.setDescription(event.getDescription());
        response.setBannerImage(event.getBannerImage());
        response.setBlocks(event.getBlocks());
        response.setGrades(event.getGrades());
        response.setStartDateTime(event.getStartDateTime());
        response.setEndDateTime(event.getEndDateTime());
        response.setCreatedAt(event.getCreatedAt());
        response.setUpdatedAt(event.getUpdatedAt());

        return Optional.of(response);
    }

    @Override
    public CreateEventResponse createEvent(CreateEventRequest request) {
        Event event = Event.builder()
                .userId(request.getUserId())
                .title(request.getTitle())
                .description(request.getDescription())
                .bannerImage(request.getBannerImage())
                .blocks(request.getBlocks())
                .grades(request.getGrades())
                .startDateTime(request.getStartDateTime())
                .endDateTime(request.getEndDateTime())
                .build();

        this.eventRepository.save(event);

        CreateEventResponse response = new CreateEventResponse();
        response.setId(event.getId());

        return response;
    }
}
