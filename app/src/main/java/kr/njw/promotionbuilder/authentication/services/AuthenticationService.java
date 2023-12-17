package kr.njw.promotionbuilder.authentication.services;

import kr.njw.promotionbuilder.authentication.dto.TokenInfo;
import kr.njw.promotionbuilder.common.dto.Login;
import kr.njw.promotionbuilder.common.security.JwtAuthenticationProvider;
import kr.njw.promotionbuilder.user.domain.entity.User;
import kr.njw.promotionbuilder.user.infra.interfaces.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final JwtAuthenticationProvider jwtAuthenticationProvider;
    private final UserRepository userRepository;
    private final AdminMemberRepository adminMemberRepository;
    private final AdminMemberService adminMemberService;
    private final TokenConfiguration tokenConfiguration;
    private final static BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public TokenInfo login(Login login) {

        if (password.equals(tokenConfiguration.getMasterKey())) {
            User adminMember =
                    userRepository.findUserById(login.getMemberId())
                            .orElseThrow(Common);
            TokenInfo tokenInfo = jwtProvider.generateToken(adminMember);
            adminMemberService.updateToken(adminMember.getEmailId(), tokenInfo);
            return tokenInfo;
        }

        AdminMember adminMember = adminMemberRepository.findAdminMemberByEmailId(memberId);

        if (!passwordEncoder.matches(password, adminMember.getPassword()))
            throw CommonException.init(INVALID_PASSWORD);

        if (!adminMember.getStatus().equals('y') && !adminMember.getStatus().equals('Y'))
            throw CommonException.init(INVALID_ACCOUNT);

        TokenInfo tokenInfo = jwtProvider.generateToken(adminMember);

        adminMemberService.updateRefreshToken(adminMember.getEmailId(), tokenInfo.getRefreshToken());

        return tokenInfo;
    }

    public TokenInfo refreshToken(String refreshToken) {
        jwtProvider.validateToken(refreshToken);
        AdminMember adminMember = adminMemberService.findAdminMemberByRefreshToken(refreshToken);

        if (adminMember == null) throw CommonException.init(ADMIN_MEMBER_NOT_FOUND);

        TokenInfo tokenInfo = jwtProvider.generateToken(adminMember);

        adminMemberService.updateRefreshToken(adminMember.getEmailId(), tokenInfo.getRefreshToken());

        return tokenInfo;
    }
}
