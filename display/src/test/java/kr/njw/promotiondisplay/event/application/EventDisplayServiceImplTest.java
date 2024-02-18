package kr.njw.promotiondisplay.event.application;

import kr.njw.promotiondisplay.common.dto.BaseResponse;
import kr.njw.promotiondisplay.event.application.dto.FindEventRequest;
import kr.njw.promotiondisplay.event.application.dto.FindEventResponse;
import kr.njw.promotiondisplay.event.entity.Event;
import kr.njw.promotiondisplay.event.entity.vo.EventImageBlock;
import kr.njw.promotiondisplay.event.entity.vo.EventLinkBlock;
import kr.njw.promotiondisplay.event.entity.vo.EventScriptBlock;
import net.datafaker.Faker;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.times;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;

@ExtendWith(MockitoExtension.class)
class EventDisplayServiceImplTest {
    private static final String MAIN_API_HOST = "https://test.com";
    private static final String MASTER_API_KEY = "master-api-key";

    @InjectMocks
    private EventDisplayServiceImpl eventDisplayServiceImpl;
    @Mock
    private RestTemplate restTemplate;
    private Random random;
    private Faker faker;
    private LocalDateTime now;

    @BeforeEach
    void setUp() {
        ReflectionTestUtils.setField(this.eventDisplayServiceImpl, "MAIN_API_HOST", MAIN_API_HOST);
        ReflectionTestUtils.setField(this.eventDisplayServiceImpl, "MASTER_API_KEY", MASTER_API_KEY);

        this.random = new Random(42);
        this.faker = new Faker(this.random);
        this.now = LocalDateTime.now();
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    @DisplayName("이벤트를 조회할 수 있어야 한다")
    void findEvent() {
        int testCount = 100;

        for (int i = 0; i < testCount; i++) {
            Event testReturnEvent = this.createTestEvents(1).get(0);

            FindEventRequest request = new FindEventRequest();
            request.setId(testReturnEvent.getId());

            given(this.restTemplate.exchange(
                    ArgumentMatchers.<String>any(), any(), any(), ArgumentMatchers.<ParameterizedTypeReference<?>>any()
            )).willAnswer(invocation -> ResponseEntity.ok(new BaseResponse<>(this.convertEventToResponse(testReturnEvent))));

            FindEventResponse response = this.eventDisplayServiceImpl.findEvent(request).orElse(null);

            then(this.restTemplate)
                    .should(times(1))
                    .exchange(
                            eq(MAIN_API_HOST + "/api/events/" + request.getId()),
                            eq(HttpMethod.GET),
                            argThat(httpEntity -> Objects.equals(httpEntity.getHeaders().getFirst(AUTHORIZATION), "Bearer " + MASTER_API_KEY)),
                            ArgumentMatchers.<ParameterizedTypeReference<?>>any()
                    );

            assertThat(response.getId()).isEqualTo(testReturnEvent.getId());
            assertThat(response.getTitle()).isEqualTo(testReturnEvent.getTitle());
            assertThat(response.getDescription()).isEqualTo(testReturnEvent.getDescription());
            assertThat(response.getBannerImage()).isEqualTo(testReturnEvent.getBannerImage());
            assertThat(response.getBlocks()).containsExactlyElementsOf(testReturnEvent.getBlocks());

            assertThat(response.getStartDateTime()).isEqualTo(testReturnEvent.getStartDateTime());
            assertThat(response.getEndDateTime()).isEqualTo(testReturnEvent.getEndDateTime());

            reset(this.restTemplate);
        }
    }

    private FindEventResponse convertEventToResponse(Event event) {
        FindEventResponse findEventResponse = new FindEventResponse();
        findEventResponse.setId(event.getId());
        findEventResponse.setTitle(event.getTitle());
        findEventResponse.setDescription(event.getDescription());
        findEventResponse.setBannerImage(event.getBannerImage());
        findEventResponse.setBlocks(event.getBlocks());
        findEventResponse.setStartDateTime(event.getStartDateTime());
        findEventResponse.setEndDateTime(event.getEndDateTime());

        return findEventResponse;
    }

    private List<Event> createTestEvents(int count) {
        List<Event> events = new ArrayList<>();

        for (int i = 0; i < count; i++) {
            Event.EventBuilder eventBuilder = Event.builder()
                    .id(this.faker.random().hex(32).toLowerCase())
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
                    .startDateTime(this.now.minusMinutes(this.faker.random().nextLong(1, 100_000)))
                    .endDateTime(this.now.plusMinutes(this.faker.random().nextLong(1, 100_000)));

            events.add(eventBuilder.build());
        }

        return events;
    }
}
