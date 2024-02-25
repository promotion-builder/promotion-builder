package kr.njw.promotioneventhost.event.application;

import kr.njw.promotioneventhost.event.application.vo.EventQueueMessage;

public interface EventInfoUpdateService {
    void update(EventQueueMessage message);
}
