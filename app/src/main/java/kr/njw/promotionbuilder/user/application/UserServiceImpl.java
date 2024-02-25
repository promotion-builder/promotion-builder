package kr.njw.promotionbuilder.user.application;


import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import kr.njw.promotionbuilder.common.dto.BaseResponseStatus;
import kr.njw.promotionbuilder.common.exception.BaseException;
import kr.njw.promotionbuilder.common.security.JwtAuthenticationProvider;
import kr.njw.promotionbuilder.common.security.Role;
import kr.njw.promotionbuilder.common.utils.AuthUtils;
import kr.njw.promotionbuilder.user.controller.dto.*;
import kr.njw.promotionbuilder.user.entity.User;
import kr.njw.promotionbuilder.user.entity.dto.UpdateUser;
import kr.njw.promotionbuilder.user.entity.mapper.UserMapper;
import kr.njw.promotionbuilder.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;


@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final JwtAuthenticationProvider jwtAuthenticationProvider;

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
    public void updateUser(String username, UserUpdateRequest userUpdateRequest) {
        User user = userRepository.findUserByUsernameAndDeletedAtNull(username)
                .orElseThrow(() -> new BaseException(BaseResponseStatus.NOT_FOUND));

        UpdateUser updateUser = userMapper.toUpdateUser(user);
        updateUser.setCompanyName(userUpdateRequest.getCompanyName());
        user.updateUser(updateUser);
    }

    @Override
    @Transactional(rollbackOn = Exception.class)
    public void updateUserPassword(String username, UserPasswordUpdateRequest userPasswordUpdateRequest) {
        User user = userRepository.findUserByUsernameAndDeletedAtNull(username)
                .orElseThrow(() -> new BaseException(BaseResponseStatus.NOT_FOUND_USER));

        UpdateUser updateUser = userMapper.toUpdateUser(user);
        updateUser.setPassword(userPasswordUpdateRequest.getPassword());
        user.updateUser(updateUser);
    }

    @Override
    @Transactional(rollbackOn = Exception.class)
    public void updateUsername(String usedUsername, UsernameUpdateRequest usernameUpdateRequest) {
        User user = userRepository.findUserByUsernameAndDeletedAtNull(usedUsername)
                .orElseThrow(() -> new BaseException(BaseResponseStatus.NOT_FOUND_USER));

        if (userRepository.findUserByUsernameAndDeletedAtNull(usernameUpdateRequest.getUsername())
                .isPresent()) {
                throw new BaseException(BaseResponseStatus.DUPLICATED_USER);
        }

        UpdateUser updateUser = userMapper.toUpdateUser(user);
        updateUser.setUsername(usernameUpdateRequest.getUsername());

        user.updateUser(updateUser);
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
