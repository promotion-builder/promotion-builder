package kr.njw.promotionbuilder.user.services;


import jakarta.transaction.Transactional;
import kr.njw.promotionbuilder.common.dto.BaseResponseStatus;
import kr.njw.promotionbuilder.common.exception.BaseException;
import kr.njw.promotionbuilder.common.security.Role;
import kr.njw.promotionbuilder.user.controller.dto.CreateUserResponse;
import kr.njw.promotionbuilder.user.controller.dto.UserDto;
import kr.njw.promotionbuilder.user.controller.dto.UserSignUpRequest;
import kr.njw.promotionbuilder.user.controller.dto.UserUpdateRequest;
import kr.njw.promotionbuilder.user.entity.User;
import kr.njw.promotionbuilder.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;


@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    @Override
    @Transactional(rollbackOn = Exception.class)
    public CreateUserResponse signUp(UserSignUpRequest userSignUpRequest) {
        User user = User.builder()
                .username(userSignUpRequest.getUsername())
                .password(User.encryptPassword(userSignUpRequest.getPassword()))
                .secretKey(User.generateRandomHexString())
                .companyName(userSignUpRequest.getCompanyName())
                .role(Role.USER)
                .status(User.UserStatus.ACTIVE)
                .build();

        userRepository.save(user);
        return CreateUserResponse.builder()
                .id(user.getId())
                .secretKey(user.getSecretKey())
                .build();
    }

    @Override
    @Transactional(rollbackOn = Exception.class)
    public void updateUser(String userId, UserUpdateRequest userUpdateRequest) {

        User user = userRepository.findUserByUsernameAndDeletedAtNull(userId)
                .orElseThrow(() -> new BaseException(BaseResponseStatus.NOT_FOUND));

        user.updateUser(userUpdateRequest.getPassword(), userUpdateRequest.getCompanyName());
    }

    @Override
    public UserDto findByUsername(String username) {
        User user = userRepository.findUserByUsernameAndDeletedAtNull(username)
                .orElseThrow(() -> new BaseException(BaseResponseStatus.NOT_FOUND));

        return UserDto.builder()
                .refreshToken(user.getRefreshToken())
                .role(user.getRole())
                .username(user.getUsername())
                .build();
    }
}
