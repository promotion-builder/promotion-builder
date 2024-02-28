package kr.njw.promotioneventhost.user.application;


import io.awspring.cloud.sqs.annotation.SqsListener;
import kr.njw.promotioneventhost.user.application.dto.RefreshUserRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class RefreshUserServiceImpl implements RefreshUserService {
    @Override
    @SqsListener("${app.sqs.user-queue-name}")
    public void update(RefreshUserRequest message) {
        log.info(message.toString());

        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
