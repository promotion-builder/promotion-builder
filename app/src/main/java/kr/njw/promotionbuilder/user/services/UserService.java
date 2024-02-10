package kr.njw.promotionbuilder.user.services;

import kr.njw.promotionbuilder.user.controller.dto.CreateUserResponse;
import kr.njw.promotionbuilder.user.controller.dto.UserDto;
import kr.njw.promotionbuilder.user.controller.dto.UserSignUpRequest;

public interface UserService {
    CreateUserResponse signUp(UserSignUpRequest userSignUpRequest);
    UserDto findByUsername(String username);
}
