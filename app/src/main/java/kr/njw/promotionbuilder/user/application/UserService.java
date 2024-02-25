package kr.njw.promotionbuilder.user.application;

import kr.njw.promotionbuilder.user.application.dto.*;

public interface UserService {
    SignUpResponse signUp(SignUpRequest request);

    void updateUserProfile(Long userId, UpdateUserProfileRequest request);

    void changeUserPassword(Long userId, ChangePasswordRequest request);

    UserResponse findByUserId(Long userId);

    boolean isUsernameUsed(String username);
}
