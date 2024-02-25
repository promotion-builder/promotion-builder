package kr.njw.promotioneventhost.event.application;

import kr.njw.promotioneventhost.event.application.dto.UpdateEventInfoRequest;

public interface UpdateEventInfoService {
    void update(UpdateEventInfoRequest message);
}
