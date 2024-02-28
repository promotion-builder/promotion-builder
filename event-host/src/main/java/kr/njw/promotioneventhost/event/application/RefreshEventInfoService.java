package kr.njw.promotioneventhost.event.application;

import kr.njw.promotioneventhost.event.application.dto.RefreshEventInfoRequest;

public interface RefreshEventInfoService {
    void update(RefreshEventInfoRequest message);
}
