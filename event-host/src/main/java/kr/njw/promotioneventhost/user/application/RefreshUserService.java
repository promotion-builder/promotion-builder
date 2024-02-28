package kr.njw.promotioneventhost.user.application;

import kr.njw.promotioneventhost.user.application.dto.RefreshUserRequest;

public interface RefreshUserService {
    void update(RefreshUserRequest message);
}
