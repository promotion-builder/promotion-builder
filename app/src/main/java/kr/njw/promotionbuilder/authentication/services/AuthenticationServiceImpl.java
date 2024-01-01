package kr.njw.promotionbuilder.authentication.services;

import kr.njw.promotionbuilder.authentication.controller.dto.TokenDtoResponse;
import kr.njw.promotionbuilder.authentication.controller.dto.TokenInfo;
import kr.njw.promotionbuilder.common.dto.BaseResponseStatus;
import kr.njw.promotionbuilder.common.dto.Login;
import kr.njw.promotionbuilder.common.exception.BaseException;
import kr.njw.promotionbuilder.common.security.JwtAuthenticationProvider;
import kr.njw.promotionbuilder.common.security.Role;
import kr.njw.promotionbuilder.user.domain.entity.User;
import kr.njw.promotionbuilder.user.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Array;
import java.util.ArrayList;
import java.util.List;


@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class AuthenticationServiceImpl implements AuthenticationService {
    private final UserRepository userRepository;
    private final JwtAuthenticationProvider jwtAuthenticationProvider;
//    private final AdminMemberRepository adminMemberRepository;
//    private final AdminMemberService adminMemberService;
//    private final TokenConfiguration tokenConfiguration;

    private final static BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public TokenDtoResponse login(Login login) {
        log.info("description : "+userRepository.count());

        User user =
                userRepository.findUserByUsername(login.getMemberId())
                        .orElseThrow(() -> new BaseException(BaseResponseStatus.BAD_REQUEST));

        if (!passwordEncoder.matches(login.getPassword(), user.getPassword()))
            throw new BaseException(BaseResponseStatus.LOGIN_ERROR);

        if (user.getStatus().equals(User.UserStatus.DELETED) ||
                user.getStatus().equals(User.UserStatus.DEACTIVATE))
            throw new BaseException(BaseResponseStatus.UNAUTHORIZED);

        List<Role> roles = new ArrayList<>();
        roles.add(user.getRole());

        return TokenDtoResponse.builder()
                .refreshToken(jwtAuthenticationProvider.getRefreshToken(user.getUsername(), roles))
                .accessToken(jwtAuthenticationProvider.createToken(user.getUsername(), roles))
                .grantType("Bearer")
                .build();
    }

    public TokenInfo refreshToken(String refreshToken) {
//        jwtProvider.validateToken(refreshToken);
//        AdminMember adminMember = adminMemberService.findAdminMemberByRefreshToken(refreshToken);
//
//        if (adminMember == null) throw CommonException.init(ADMIN_MEMBER_NOT_FOUND);
//
//        TokenInfo tokenInfo = jwtProvider.generateToken(adminMember);
//
//        adminMemberService.updateRefreshToken(adminMember.getEmailId(), tokenInfo.getRefreshToken());
//
//        return tokenInfo;
        return null;
    }
}
