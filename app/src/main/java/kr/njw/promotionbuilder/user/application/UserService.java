package kr.njw.promotionbuilder.user.application;

import jakarta.servlet.http.HttpServletRequest;
import kr.njw.promotionbuilder.user.controller.dto.*;

public interface UserService {
    CreateUserResponse signUp(UserSignUpRequest userSignUpRequest);

    void updateUser(String username, UserUpdateRequest userUpdateRequest);

    void updateUserPassword(String username, UserPasswordUpdateRequest userPasswordUpdateRequest);

    void updateUsername(String username, UsernameUpdateRequest usernameUpdateRequest);

    UserDto findByUsername(String username);
}
