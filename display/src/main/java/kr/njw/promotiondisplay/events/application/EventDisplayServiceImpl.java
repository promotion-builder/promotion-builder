package kr.njw.promotiondisplay.events.application;

import kr.njw.promotiondisplay.common.dto.BaseResponse;
import kr.njw.promotiondisplay.events.application.dto.FindEventRequest;
import kr.njw.promotiondisplay.events.application.dto.FindEventResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.Objects;
import java.util.Optional;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;

@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class EventDisplayServiceImpl implements EventDisplayService {
    @Value("${app.main-api-host}")
    private final String MAIN_API_HOST;
    @Value("${app.security.master-api-key}")
    private final String MASTER_API_KEY;

    private final RestTemplate restTemplate;

    @Override
    public Optional<FindEventResponse> findEvent(FindEventRequest request) {
        HttpHeaders headers = new HttpHeaders();
        headers.add(AUTHORIZATION, "Bearer " + this.MASTER_API_KEY);

        HttpEntity<?> httpEntity = new HttpEntity<>(headers);

        try {
            ResponseEntity<BaseResponse<FindEventResponse>> responseEntity = this.restTemplate.exchange(
                    MAIN_API_HOST + "/api/events/" + request.getId(),
                    HttpMethod.GET,
                    httpEntity,
                    new ParameterizedTypeReference<>() {
                    }
            );

            return Optional.ofNullable(Objects.requireNonNull(responseEntity.getBody()).getResult());
        } catch (HttpClientErrorException e) {
            log.info("findEvent client error", e);
            return Optional.empty();
        } catch (HttpServerErrorException e) {
            log.info("findEvent server error", e);
            throw e;
        }
    }
}