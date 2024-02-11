package kr.njw.promotionbuilder.event.application;

import kr.njw.promotionbuilder.event.application.dto.FindEventRequest;
import kr.njw.promotionbuilder.event.application.dto.FindEventResponse;
import kr.njw.promotionbuilder.event.application.dto.FindEventsRequest;
import kr.njw.promotionbuilder.event.application.dto.FindEventsResponse;
import kr.njw.promotionbuilder.event.entity.Event;
import kr.njw.promotionbuilder.event.entity.vo.EventImageBlock;
import kr.njw.promotionbuilder.event.entity.vo.EventLinkBlock;
import kr.njw.promotionbuilder.event.entity.vo.EventScriptBlock;
import kr.njw.promotionbuilder.event.repository.EventRepository;
import net.datafaker.Faker;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
class EventServiceImplTest {
    @InjectMocks
    private EventServiceImpl eventService;
    @Mock
    private EventRepository eventRepository;
    private Random random;
    private Faker faker;
    private LocalDateTime now;

    @BeforeEach
    void setUp() {
        this.random = new Random(42);
        this.faker = new Faker(this.random);
        this.now = LocalDateTime.now();
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    @DisplayName("이벤트 목록 페이지를 조회할 수 있어야 한다")
    void findEvents() {
        long testUserId = 3L;
        int testPage = 3;
        int testPagingSize = 500;
        List<Event> testReturnEvents = this.createTestEvents(testPagingSize / 2);
        Page<Event> testReturnPage = new PageImpl<>(testReturnEvents, PageRequest.of(8, testReturnEvents.size()), testReturnEvents.size() * 5L);

        given(this.eventRepository.findByUserIdAndDeletedAtNullOrderByCreatedAtDesc(any(), any())).willReturn(testReturnPage);

        FindEventsRequest request = new FindEventsRequest();
        request.setUserId(testUserId);
        request.setPage(testPage);
        request.setPagingSize(testPagingSize);

        FindEventsResponse response = this.eventService.findEvents(request);

        then(this.eventRepository)
                .should(times(1))
                .findByUserIdAndDeletedAtNullOrderByCreatedAtDesc(
                        eq(testUserId), argThat(pageable -> pageable.getPageNumber() == testPage - 1 && pageable.getPageSize() == testPagingSize)
                );

        assertThat(response.getPage()).isEqualTo(testReturnPage.getNumber() + 1);
        assertThat(response.getPageSize()).isEqualTo(testReturnPage.getSize());
        assertThat(response.getPagingSize()).isEqualTo(testReturnPage.getNumberOfElements());
        assertThat(response.getTotalPage()).isEqualTo(testReturnPage.getTotalPages());
        assertThat(response.getTotalSize()).isEqualTo(testReturnPage.getTotalElements());
        assertThat(response.getEvents().size()).isEqualTo(testReturnPage.getNumberOfElements());
        assertThat(response.getEvents().size()).isEqualTo(testReturnEvents.size());
    }

    @Test
    @DisplayName("이벤트 목록 페이지의 최대 사이즈는 1000개이다")
    void findEventsLimit() {
        final int PAGE_LIMIT = 1000;

        long testUserId = 42L;
        int testPage = 1;
        int testPagingSize = PAGE_LIMIT * 4;
        List<Event> testReturnEvents = this.createTestEvents(PAGE_LIMIT);
        Page<Event> testReturnPage = new PageImpl<>(testReturnEvents, PageRequest.of(0, testReturnEvents.size()), testReturnEvents.size());

        given(this.eventRepository.findByUserIdAndDeletedAtNullOrderByCreatedAtDesc(any(), any())).willReturn(testReturnPage);

        FindEventsRequest request = new FindEventsRequest();
        request.setUserId(testUserId);
        request.setPage(testPage);
        request.setPagingSize(testPagingSize);

        FindEventsResponse response = this.eventService.findEvents(request);

        then(this.eventRepository)
                .should(times(1))
                .findByUserIdAndDeletedAtNullOrderByCreatedAtDesc(
                        eq(testUserId), argThat(pageable -> pageable.getPageNumber() == testPage - 1 && pageable.getPageSize() == PAGE_LIMIT)
                );

        assertThat(response.getPage()).isEqualTo(testReturnPage.getNumber() + 1);
        assertThat(response.getPageSize()).isEqualTo(testReturnPage.getSize());
        assertThat(response.getPagingSize()).isEqualTo(testReturnPage.getNumberOfElements());
        assertThat(response.getTotalPage()).isEqualTo(testReturnPage.getTotalPages());
        assertThat(response.getTotalSize()).isEqualTo(testReturnPage.getTotalElements());
        assertThat(response.getEvents().size()).isEqualTo(testReturnPage.getNumberOfElements());
        assertThat(response.getEvents().size()).isEqualTo(testReturnEvents.size());
    }

    @Test
    @DisplayName("이벤트 목록 페이지의 blocks 반환값은 null 이다")
    void findEventsBlocksNull() {
        long testUserId = 42L;
        int testPage = 1;
        int testPagingSize = 100;
        List<Event> testReturnEvents = this.createTestEvents(testPagingSize);
        Page<Event> testReturnPage = new PageImpl<>(testReturnEvents, PageRequest.of(0, testReturnEvents.size()), testReturnEvents.size());

        given(this.eventRepository.findByUserIdAndDeletedAtNullOrderByCreatedAtDesc(any(), any())).willReturn(testReturnPage);

        FindEventsRequest request = new FindEventsRequest();
        request.setUserId(testUserId);
        request.setPage(testPage);
        request.setPagingSize(testPagingSize);

        FindEventsResponse response = this.eventService.findEvents(request);

        response.getEvents().forEach(findEventResponse -> assertThat(findEventResponse.getBlocks()).isNull());
    }

    @Test
    @DisplayName("이벤트 상세를 조회할 수 있어야 한다")
    void findEvent() {
        int testCount = 100;

        for (int i = 0; i < testCount; i++) {
            Event testReturnEvent = this.createTestEvents(1).get(0);

            given(this.eventRepository.findByIdAndDeletedAtNull(testReturnEvent.getId())).willReturn(Optional.of(testReturnEvent));

            FindEventRequest request = new FindEventRequest();
            request.setId(testReturnEvent.getId());
            request.setUserId(testReturnEvent.getUserId());

            FindEventResponse response = this.eventService.findEvent(request).orElse(null);

            then(this.eventRepository)
                    .should(times(1))
                    .findByIdAndDeletedAtNull(eq(testReturnEvent.getId()));

            assertThat(response.getId()).isEqualTo(testReturnEvent.getId());
            assertThat(response.getUserId()).isEqualTo(testReturnEvent.getUserId());
            assertThat(response.getTitle()).isEqualTo(testReturnEvent.getTitle());
            assertThat(response.getDescription()).isEqualTo(testReturnEvent.getDescription());
            assertThat(response.getBannerImage()).isEqualTo(testReturnEvent.getBannerImage());

            response.getBlocks().forEach(eventBlock -> assertThat(testReturnEvent.getBlocks().contains(eventBlock)).isTrue());
            response.getGrades().forEach(grade -> assertThat(testReturnEvent.getGrades().contains(grade)).isTrue());

            assertThat(response.getStartDateTime()).isEqualTo(testReturnEvent.getStartDateTime());
            assertThat(response.getEndDateTime()).isEqualTo(testReturnEvent.getEndDateTime());
            assertThat(response.getCreatedAt()).isEqualTo(testReturnEvent.getCreatedAt());
            assertThat(response.getUpdatedAt()).isEqualTo(testReturnEvent.getUpdatedAt());
        }
    }

    /*
    @Test
    void createEvent() {
    }

    @Test
    void editEvent() {
    }

    @Test
    void deleteEvent() {
    }
    */

    private List<Event> createTestEvents(int count) {
        List<Event> events = new ArrayList<>();

        for (int i = 0; i < count; i++) {
            Event event = Event.builder()
                    .id(this.faker.random().hex(32))
                    .userId(this.faker.random().nextLong())
                    .title(this.faker.book().title())
                    .description(this.faker.restaurant().description())
                    .bannerImage(this.faker.internet().url())
                    .blocks(this.faker.collection(
                            () -> {
                                EventImageBlock eventImageBlock = new EventImageBlock();
                                eventImageBlock.setImage(this.faker.internet().url());
                                return eventImageBlock;
                            },
                            () -> {
                                EventLinkBlock eventLinkBlock = new EventLinkBlock();
                                eventLinkBlock.setImage(this.faker.internet().url());
                                eventLinkBlock.setUrl(this.faker.internet().url());
                                eventLinkBlock.setOpenType(this.faker.options().option(EventLinkBlock.OpenType.class));
                                return eventLinkBlock;
                            },
                            () -> {
                                EventScriptBlock eventScriptBlock = new EventScriptBlock();
                                eventScriptBlock.setImage(this.faker.internet().url());
                                eventScriptBlock.setScript(this.faker.lorem().paragraph());
                                return eventScriptBlock;
                            }
                    ).len(0, 10).generate())
                    .grades(this.faker.collection(() -> this.faker.beer().brand()).nullRate(0.2).len(0, 5).generate())
                    .startDateTime(this.now.minusMinutes(this.faker.random().nextLong(1, 100_000)))
                    .endDateTime(this.now.plusMinutes(this.faker.random().nextLong(1, 100_000)))
                    .createdAt(this.now.minusMinutes(this.faker.random().nextLong(1, 100_000)))
                    .updatedAt(this.now.minusMinutes(this.faker.random().nextLong(1, 100_000)))
                    .deletedAt(null)
                    .build();

            events.add(event);
        }

        return events;
    }
}
