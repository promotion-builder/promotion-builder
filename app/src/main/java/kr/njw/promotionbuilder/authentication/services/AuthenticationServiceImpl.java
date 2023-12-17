package kr.njw.promotionbuilder.authentication.services;

import kr.njw.promotionbuilder.authentication.controller.dto.TokenInfo;
import kr.njw.promotionbuilder.common.dto.BaseResponseStatus;
import kr.njw.promotionbuilder.common.dto.Login;
import kr.njw.promotionbuilder.common.exception.BaseException;
import kr.njw.promotionbuilder.common.security.JwtAuthenticationProvider;
import kr.njw.promotionbuilder.user.domain.entity.User;
import kr.njw.promotionbuilder.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {
    private final JwtAuthenticationProvider jwtAuthenticationProvider;
    private final UserRepository userRepository;
//    private final AdminMemberRepository adminMemberRepository;
//    private final AdminMemberService adminMemberService;
//    private final TokenConfiguration tokenConfiguration;

    private final static BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public TokenInfo login(Login login) {

        User user =
                userRepository.findUserById(login.getMemberId())
                        .orElseThrow(() -> new BaseException(BaseResponseStatus.BAD_REQUEST));

        if (!passwordEncoder.matches(login.getPassword(), user.getPassword()))
            throw new BaseException(BaseResponseStatus.LOGIN_ERROR);

        if (user.getStatus().equals(User.UserStatus.DELETED) || user.getStatus().equals(User.UserStatus.DEACTIVATE))
            throw new BaseException(BaseResponseStatus.UNAUTHORIZED);

        jwtAuthenticationProvider.createToken(user.getUsername(), )

//        TokenInfo tokenInfo = jwtProvider.generateToken(adminMember);
//
//        adminMemberService.updateRefreshToken(adminMember.getEmailId(), tokenInfo.getRefreshToken());
//
//        return tokenInfo;
        return null;
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
