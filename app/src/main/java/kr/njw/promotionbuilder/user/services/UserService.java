package kr.njw.promotionbuilder.user.services;

import jakarta.transaction.UserTransaction;
import kr.njw.promotionbuilder.user.controller.dto.CreateUserResponse;
import kr.njw.promotionbuilder.user.controller.dto.UserDto;
import kr.njw.promotionbuilder.user.controller.dto.UserSignUpRequest;
import kr.njw.promotionbuilder.user.controller.dto.UserUpdateRequest;
import kr.njw.promotionbuilder.user.entity.User;

public interface UserService {
    CreateUserResponse signUp(UserSignUpRequest userSignUpRequest);

    void updateUser(String userId, UserUpdateRequest userUpdateRequest);
    UserDto findByUsername(String username);
}
