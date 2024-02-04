package kr.njw.promotionbuilder.common.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import kr.njw.promotionbuilder.common.utils.AuthUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collection;
import java.util.List;

// 총관리자 전용 마스터 권한 인증 필터
// Authorization: Bearer <MASTER_API_KEY>.<USER_ID> 형식의 헤더로 인증
// <USER_ID>는 optional

@Slf4j
@RequiredArgsConstructor
public class MasterAuthenticationFilter extends OncePerRequestFilter {
    private final String MASTER_API_KEY;

    @Override
    protected void doFilterInternal(@NotNull HttpServletRequest request, @NotNull HttpServletResponse response,
                                    @NotNull FilterChain filterChain) throws ServletException, IOException {
        String token = AuthUtils.getAccessToken(request);
        String apiKey = StringUtils.substringBefore(token, ".");
        String userId = StringUtils.defaultIfBlank(StringUtils.substringAfter(token, "."), "0");

        if (apiKey.equals(this.MASTER_API_KEY)) {
            Collection<? extends GrantedAuthority> authorities = List.of(new SimpleGrantedAuthority(Role.ADMIN.toAuthority()));
            UserDetails userDetails = new User(userId, "", authorities);

            SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(userDetails, "", authorities));
        }

        filterChain.doFilter(request, response);
    }
}
