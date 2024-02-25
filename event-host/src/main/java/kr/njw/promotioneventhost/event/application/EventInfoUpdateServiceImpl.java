package kr.njw.promotioneventhost.event.application;


import io.awspring.cloud.sqs.annotation.SqsListener;
import kr.njw.promotioneventhost.event.application.vo.EventQueueMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class EventInfoUpdateServiceImpl implements EventInfoUpdateService {
    @Override
    @SqsListener("${app.sqs.event-queue-name}")
    public void update(EventQueueMessage message) {
        log.info(message.toString());

        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
