package kr.njw.promotionbuilder.user.application;


import kr.njw.promotionbuilder.common.dto.BaseResponseStatus;
import kr.njw.promotionbuilder.common.exception.BaseException;
import kr.njw.promotionbuilder.common.security.Role;
import kr.njw.promotionbuilder.user.application.dto.*;
import kr.njw.promotionbuilder.user.entity.User;
import kr.njw.promotionbuilder.user.entity.dto.UserProfile;
import kr.njw.promotionbuilder.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserProfile.Factory userProfileFactory;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public SignUpResponse signUp(SignUpRequest request) {
        if (this.userRepository.findByUsername(request.getUsername()).isPresent()) {
            throw new BaseException(BaseResponseStatus.DUPLICATED_USERNAME);
        }

        User user = User.builder()
                .username(request.getUsername())
                .companyName(request.getCompanyName())
                .password(User.createPassword(request.getPassword(), this.passwordEncoder))
                .secretKey(User.generateSecretKey())
                .refreshToken(null)
                .status(User.UserStatus.ACTIVE)
                .role(Role.USER)
                .build();

        this.userRepository.saveAndFlush(user);

        return SignUpResponse.builder()
                .id(user.getId())
                .build();
    }

    @Override
    @Transactional
    public void updateUserProfile(Long userId, UpdateUserProfileRequest request) {
        User user = this.userRepository.findByIdAndDeletedAtNull(userId)
                .orElseThrow(() -> new BaseException(BaseResponseStatus.NOT_FOUND));

        UserProfile profile = this.userProfileFactory.generate();
        profile.setCompanyName(request.getCompanyName());

        user.updateProfile(profile);
        this.userRepository.save(user);
    }

    @Override
    @Transactional
    public void changeUserPassword(Long userId, ChangePasswordRequest request) {
        User user = this.userRepository.findByIdAndDeletedAtNull(userId)
                .orElseThrow(() -> new BaseException(BaseResponseStatus.NOT_FOUND));

        if (!this.passwordEncoder.matches(request.getOldPassword(), user.getPassword().toString())) {
            throw new BaseException(BaseResponseStatus.INVALID_PASSWORD);
        }

        user.changePassword(User.createPassword(request.getNewPassword(), this.passwordEncoder));
        this.userRepository.save(user);
    }

    @Override
    @Transactional(readOnly = true)
    public UserResponse findByUserId(Long userId) {
        User user = this.userRepository.findByIdAndDeletedAtNull(userId)
                .orElseThrow(() -> new BaseException(BaseResponseStatus.NOT_FOUND));

        UserResponse userResponse = new UserResponse();
        userResponse.setId(user.getId());
        userResponse.setUsername(user.getUsername());
        userResponse.setCompanyName(user.getCompanyName());
        userResponse.setSecretKey(user.getSecretKey().toString());
        userResponse.setRole(user.getRole());
        return userResponse;
    }
}
