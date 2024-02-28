package kr.njw.promotionbuilder.event.application;

import io.awspring.cloud.sns.core.SnsTemplate;
import kr.njw.promotionbuilder.common.dto.BaseResponseStatus;
import kr.njw.promotionbuilder.common.exception.BaseException;
import kr.njw.promotionbuilder.event.application.dto.*;
import kr.njw.promotionbuilder.event.entity.Event;
import kr.njw.promotionbuilder.event.entity.vo.EventBlock;
import kr.njw.promotionbuilder.event.repository.EventRepository;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class EventServiceImpl implements EventService {
    @Value("${app.sns.event-topic-name}")
    private final String SNS_EVENT_TOPIC_NAME;

    private final EventRepository eventRepository;
    private final SnsTemplate snsTemplate;

    @Override
    @Transactional(readOnly = true)
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
    @Transactional(readOnly = true)
    public Optional<FindEventResponse> findEvent(FindEventRequest request) {
        Event event = this.eventRepository.findByIdAndDeletedAtNull(request.getId()).orElse(null);

        if (event == null ||
                ObjectUtils.defaultIfNull(request.getUserId(), 0L) != 0L && !Objects.equals(event.getUserId(), request.getUserId())) {
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
    @Transactional
    public CreateEventResponse createEvent(CreateEventRequest request) {
        request.getBlocks().forEach(EventBlock::issueId);

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

        this.sendEventChangeNotification(event.getId());
        return response;
    }

    @Override
    @Transactional
    public EditEventResponse editEvent(EditEventRequest request) {
        Event oldEvent = this.eventRepository.findByIdAndDeletedAtNull(request.getId()).orElse(null);

        if (oldEvent == null || !Objects.equals(oldEvent.getUserId(), request.getUserId())) {
            throw new BaseException(BaseResponseStatus.NOT_FOUND);
        }

        request.getBlocks().forEach(EventBlock::issueId);

        Event newEvent = Event.builder()
                .id(oldEvent.getId())
                .userId(oldEvent.getUserId())
                .title(request.getTitle())
                .description(request.getDescription())
                .bannerImage(request.getBannerImage())
                .blocks(request.getBlocks())
                .grades(request.getGrades())
                .startDateTime(request.getStartDateTime())
                .endDateTime(request.getEndDateTime())
                .build();

        this.eventRepository.save(newEvent);

        EditEventResponse response = new EditEventResponse();
        response.setId(newEvent.getId());

        this.sendEventChangeNotification(newEvent.getId());
        return response;
    }

    @Override
    @Transactional
    public void deleteEvent(DeleteEventRequest request) {
        Event event = this.eventRepository.findByIdAndDeletedAtNull(request.getId()).orElse(null);

        if (event == null || !Objects.equals(event.getUserId(), request.getUserId())) {
            return;
        }

        event.delete();
        this.eventRepository.save(event);
        this.sendEventChangeNotification(event.getId());
    }

    private void sendEventChangeNotification(String eventId) {
        EventChangeNotification notification = new EventChangeNotification();
        notification.setEventId(eventId);

        this.snsTemplate.sendNotification(SNS_EVENT_TOPIC_NAME, notification, null);
    }
}
