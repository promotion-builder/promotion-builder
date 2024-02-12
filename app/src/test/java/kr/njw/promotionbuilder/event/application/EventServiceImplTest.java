package kr.njw.promotionbuilder.event.application;

import kr.njw.promotionbuilder.common.dto.BaseResponseStatus;
import kr.njw.promotionbuilder.common.exception.BaseException;
import kr.njw.promotionbuilder.event.application.dto.*;
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
import org.springframework.test.util.ReflectionTestUtils;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowableOfType;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.*;
import static org.mockito.Mockito.reset;
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

        IntStream.range(0, response.getEvents().size()).forEach(idx -> {
            FindEventResponse findEventResponse = response.getEvents().get(idx);
            Event testReturnEvent = testReturnEvents.get(idx);

            assertThat(findEventResponse.getId()).isEqualTo(testReturnEvent.getId());
            assertThat(findEventResponse.getUserId()).isEqualTo(testReturnEvent.getUserId());
            assertThat(findEventResponse.getTitle()).isEqualTo(testReturnEvent.getTitle());
            assertThat(findEventResponse.getDescription()).isEqualTo(testReturnEvent.getDescription());
            assertThat(findEventResponse.getBannerImage()).isEqualTo(testReturnEvent.getBannerImage());

            if (findEventResponse.getGrades() == null) {
                assertThat(testReturnEvent.getGrades()).isNull();
            } else {
                assertThat(findEventResponse.getGrades()).containsExactlyElementsOf(testReturnEvent.getGrades());
            }

            assertThat(findEventResponse.getStartDateTime()).isEqualTo(testReturnEvent.getStartDateTime());
            assertThat(findEventResponse.getEndDateTime()).isEqualTo(testReturnEvent.getEndDateTime());
            assertThat(findEventResponse.getCreatedAt()).isEqualTo(testReturnEvent.getCreatedAt());
            assertThat(findEventResponse.getUpdatedAt()).isEqualTo(testReturnEvent.getUpdatedAt());
        });
    }

    @Test
    @DisplayName("이벤트 목록 페이지의 최대 사이즈는 1000이다")
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
    @DisplayName("이벤트 목록 페이지의 blocks 반환값은 null이다")
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
            assertThat(response.getBlocks()).containsExactlyElementsOf(testReturnEvent.getBlocks());

            if (response.getGrades() == null) {
                assertThat(testReturnEvent.getGrades()).isNull();
            } else {
                assertThat(response.getGrades()).containsExactlyElementsOf(testReturnEvent.getGrades());
            }

            assertThat(response.getStartDateTime()).isEqualTo(testReturnEvent.getStartDateTime());
            assertThat(response.getEndDateTime()).isEqualTo(testReturnEvent.getEndDateTime());
            assertThat(response.getCreatedAt()).isEqualTo(testReturnEvent.getCreatedAt());
            assertThat(response.getUpdatedAt()).isEqualTo(testReturnEvent.getUpdatedAt());

            reset(this.eventRepository);
        }
    }


    @Test
    @DisplayName("이벤트 상세 조회시 이벤트가 없으면 empty를 반환한다")
    void findEventEmpty() {
        String testEventId = "test";
        long testUserId = 42L;

        given(this.eventRepository.findByIdAndDeletedAtNull(any())).willReturn(Optional.empty());

        FindEventRequest request = new FindEventRequest();
        request.setId(testEventId);
        request.setUserId(testUserId);

        FindEventResponse response = this.eventService.findEvent(request).orElse(null);

        then(this.eventRepository)
                .should(times(1))
                .findByIdAndDeletedAtNull(eq(testEventId));

        assertThat(response).isNull();
    }

    @Test
    @DisplayName("이벤트 상세 조회시 입력된 유저 ID가 다르면 empty를 반환한다")
    void findEventInvalidUser() {
        Event testReturnEvent = this.createTestEvents(1).get(0);
        long testInvalidUserId = testReturnEvent.getUserId() - 1;

        given(this.eventRepository.findByIdAndDeletedAtNull(testReturnEvent.getId())).willReturn(Optional.of(testReturnEvent));

        FindEventRequest request = new FindEventRequest();
        request.setId(testReturnEvent.getId());
        request.setUserId(testInvalidUserId);

        FindEventResponse response = this.eventService.findEvent(request).orElse(null);

        then(this.eventRepository)
                .should(times(1))
                .findByIdAndDeletedAtNull(eq(testReturnEvent.getId()));

        assertThat(response).isNull();
    }


    @Test
    @DisplayName("이벤트 상세 조회시 입력된 유저 ID가 null 또는 0이면 이벤트를 반환한다")
    void findEventNoUserCondition() {
        Event testReturnEvent = this.createTestEvents(1).get(0);
        List<Long> testUserIds = Arrays.asList(0L, null);

        for (int i = 0; i < testUserIds.size(); i++) {
            Long testUserId = testUserIds.get(i);

            given(this.eventRepository.findByIdAndDeletedAtNull(testReturnEvent.getId())).willReturn(Optional.of(testReturnEvent));

            FindEventRequest request = new FindEventRequest();
            request.setId(testReturnEvent.getId());
            request.setUserId(testUserId);

            FindEventResponse response = this.eventService.findEvent(request).orElse(null);

            then(this.eventRepository)
                    .should(times(1))
                    .findByIdAndDeletedAtNull(eq(testReturnEvent.getId()));

            assertThat(response).isNotNull();

            reset(this.eventRepository);
        }
    }

    @Test
    @DisplayName("이벤트 생성을 할 수 있어야 한다")
    void createEvent() {
        int testCount = 100;

        for (int i = 0; i < testCount; i++) {
            Event testEvent = this.createTestEvents(1).get(0);

            CreateEventRequest request = new CreateEventRequest();
            request.setUserId(testEvent.getUserId());
            request.setTitle(testEvent.getTitle());
            request.setDescription(testEvent.getDescription());
            request.setBannerImage(testEvent.getBannerImage());
            request.setBlocks(testEvent.getBlocks());
            request.setGrades(testEvent.getGrades());
            request.setStartDateTime(testEvent.getStartDateTime());
            request.setEndDateTime(testEvent.getEndDateTime());

            willAnswer(invocation -> {
                Event event = invocation.getArgument(0);
                ReflectionTestUtils.setField(event, "id", testEvent.getId());
                return null;
            }).given(this.eventRepository).save(any());

            CreateEventResponse response = this.eventService.createEvent(request);

            then(this.eventRepository)
                    .should(times(1))
                    .save(argThat(event -> event.getUserId().equals(testEvent.getUserId()) &&
                            event.getTitle().equals(testEvent.getTitle()) &&
                            event.getDescription().equals(testEvent.getDescription()) &&
                            event.getBannerImage().equals(testEvent.getBannerImage()) &&
                            event.getBlocks() == testEvent.getBlocks() &&
                            event.getGrades() == testEvent.getGrades() &&
                            event.getStartDateTime().equals(testEvent.getStartDateTime()) &&
                            event.getEndDateTime().equals(testEvent.getEndDateTime()) &&
                            event.getDeletedAt() == null
                    ));

            assertThat(response.getId()).isEqualTo(testEvent.getId());

            reset(this.eventRepository);
        }
    }

    @Test
    @DisplayName("이벤트 수정을 할 수 있어야 한다")
    void editEvent() {
        int testCount = 100;

        for (int i = 0; i < testCount; i++) {
            Event testBaseEvent = this.createTestEvents(1).get(0);
            Event testOverwriteEvent = this.createTestEvents(1).get(0);

            EditEventRequest request = new EditEventRequest();
            request.setId(testBaseEvent.getId());
            request.setUserId(testBaseEvent.getUserId());
            request.setTitle(testOverwriteEvent.getTitle());
            request.setDescription(testOverwriteEvent.getDescription());
            request.setBannerImage(testOverwriteEvent.getBannerImage());
            request.setBlocks(testOverwriteEvent.getBlocks());
            request.setGrades(testOverwriteEvent.getGrades());
            request.setStartDateTime(testOverwriteEvent.getStartDateTime());
            request.setEndDateTime(testOverwriteEvent.getEndDateTime());

            given(this.eventRepository.findByIdAndDeletedAtNull(testBaseEvent.getId())).willReturn(Optional.of(testBaseEvent));

            EditEventResponse response = this.eventService.editEvent(request);

            then(this.eventRepository)
                    .should(times(1))
                    .save(argThat(event -> event.getUserId().equals(testBaseEvent.getUserId()) &&
                            event.getTitle().equals(testOverwriteEvent.getTitle()) &&
                            event.getDescription().equals(testOverwriteEvent.getDescription()) &&
                            event.getBannerImage().equals(testOverwriteEvent.getBannerImage()) &&
                            event.getBlocks() == testOverwriteEvent.getBlocks() &&
                            event.getGrades() == testOverwriteEvent.getGrades() &&
                            event.getStartDateTime().equals(testOverwriteEvent.getStartDateTime()) &&
                            event.getEndDateTime().equals(testOverwriteEvent.getEndDateTime()) &&
                            event.getDeletedAt() == null
                    ));

            assertThat(response.getId()).isEqualTo(testBaseEvent.getId());

            reset(this.eventRepository);
        }
    }

    @Test
    @DisplayName("이벤트 수정시 이벤트가 없으면 NOT_FOUND 오류가 발생한다")
    void editEventNotFound() {
        Event testBaseEvent = this.createTestEvents(1).get(0);
        Event testOverwriteEvent = this.createTestEvents(1).get(0);

        EditEventRequest request = new EditEventRequest();
        request.setId(testBaseEvent.getId());
        request.setUserId(testBaseEvent.getUserId());
        request.setTitle(testOverwriteEvent.getTitle());
        request.setDescription(testOverwriteEvent.getDescription());
        request.setBannerImage(testOverwriteEvent.getBannerImage());
        request.setBlocks(testOverwriteEvent.getBlocks());
        request.setGrades(testOverwriteEvent.getGrades());
        request.setStartDateTime(testOverwriteEvent.getStartDateTime());
        request.setEndDateTime(testOverwriteEvent.getEndDateTime());

        given(this.eventRepository.findByIdAndDeletedAtNull(any())).willReturn(Optional.empty());

        BaseException exception = catchThrowableOfType(() -> this.eventService.editEvent(request), BaseException.class);

        then(this.eventRepository)
                .should(times(0))
                .save(any());

        assertThat(exception.getStatus()).isSameAs(BaseResponseStatus.NOT_FOUND);
    }


    @Test
    @DisplayName("이벤트 수정시 입력된 유저 ID가 다르면 NOT_FOUND 오류가 발생한다")
    void editEventInvalidUser() {
        Event testBaseEvent = this.createTestEvents(1).get(0);
        Event testOverwriteEvent = this.createTestEvents(1).get(0);

        EditEventRequest request = new EditEventRequest();
        request.setId(testBaseEvent.getId());
        request.setUserId(testBaseEvent.getUserId() - 1);
        request.setTitle(testOverwriteEvent.getTitle());
        request.setDescription(testOverwriteEvent.getDescription());
        request.setBannerImage(testOverwriteEvent.getBannerImage());
        request.setBlocks(testOverwriteEvent.getBlocks());
        request.setGrades(testOverwriteEvent.getGrades());
        request.setStartDateTime(testOverwriteEvent.getStartDateTime());
        request.setEndDateTime(testOverwriteEvent.getEndDateTime());

        given(this.eventRepository.findByIdAndDeletedAtNull(testBaseEvent.getId())).willReturn(Optional.of(testBaseEvent));

        BaseException exception = catchThrowableOfType(() -> this.eventService.editEvent(request), BaseException.class);

        then(this.eventRepository)
                .should(times(0))
                .save(any());

        assertThat(exception.getStatus()).isSameAs(BaseResponseStatus.NOT_FOUND);
    }

    @Test
    @DisplayName("이벤트 삭제를 할 수 있어야 한다")
    void deleteEvent() {
        int testCount = 100;

        for (int i = 0; i < testCount; i++) {
            Event testReturnEvent = spy(this.createTestEvents(1).get(0));

            given(this.eventRepository.findByIdAndDeletedAtNull(testReturnEvent.getId())).willReturn(Optional.of(testReturnEvent));

            DeleteEventRequest request = new DeleteEventRequest();
            request.setId(testReturnEvent.getId());
            request.setUserId(testReturnEvent.getUserId());

            this.eventService.deleteEvent(request);

            then(this.eventRepository)
                    .should(times(1))
                    .save(same(testReturnEvent));
            then(testReturnEvent).should(times(1)).delete();
        }
    }

    @Test
    @DisplayName("이벤트 삭제시 입력된 유저 ID가 다르면 삭제되지 않는다")
    void deleteEventInvalidUser() {
        Event testReturnEvent = spy(this.createTestEvents(1).get(0));
        long testInvalidUserId = testReturnEvent.getUserId() + 1;

        given(this.eventRepository.findByIdAndDeletedAtNull(testReturnEvent.getId())).willReturn(Optional.of(testReturnEvent));

        DeleteEventRequest request = new DeleteEventRequest();
        request.setId(testReturnEvent.getId());
        request.setUserId(testInvalidUserId);

        this.eventService.deleteEvent(request);

        then(this.eventRepository)
                .should(times(0))
                .save(any());
        then(testReturnEvent).should(times(0)).delete();
    }

    private List<Event> createTestEvents(int count) {
        List<Event> events = new ArrayList<>();

        for (int i = 0; i < count; i++) {
            Event.EventBuilder eventBuilder = Event.builder()
                    .id(this.faker.random().hex(32).toLowerCase())
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
                    .grades(this.faker.collection(() -> this.faker.beer().brand()).len(0, 5).generate())
                    .startDateTime(this.now.minusMinutes(this.faker.random().nextLong(1, 100_000)))
                    .endDateTime(this.now.plusMinutes(this.faker.random().nextLong(1, 100_000)))
                    .createdAt(this.now.minusMinutes(this.faker.random().nextLong(1, 100_000)))
                    .updatedAt(this.now.minusMinutes(this.faker.random().nextLong(1, 100_000)))
                    .deletedAt(null);

            if (this.faker.random().nextDouble() <= 0.2) {
                eventBuilder.grades(null);
            }

            events.add(eventBuilder.build());
        }

        return events;
    }
}
