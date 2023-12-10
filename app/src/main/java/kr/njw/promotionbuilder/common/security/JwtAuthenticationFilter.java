package kr.njw.promotionbuilder.common.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import kr.njw.promotionbuilder.common.dto.BaseResponse;
import kr.njw.promotionbuilder.common.dto.BaseResponseStatus;
import kr.njw.promotionbuilder.common.exception.BaseException;
import kr.njw.promotionbuilder.common.utils.AuthUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.PatternMatchUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Optional;

import static kr.njw.promotionbuilder.common.dto.BaseResponseStatus.UNAUTHORIZED;

@Slf4j
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final JwtAuthenticationProvider jwtAuthenticationProvider;

    public static final String[] whiteListUris = new String[]{
            "/swagger-resources/**",
            "/swagger-ui.html",
            "/swagger-ui*/**",
            "/webjars/**",
            "/swagger/**",
            "/v1/token/login",
            "/index.html",
            "/v1/image/**"
    };

    @Override
    protected void doFilterInternal(@NotNull HttpServletRequest request, @NotNull HttpServletResponse response,
                                    @NotNull FilterChain filterChain) throws ServletException, IOException {
        if (isWhiteListURI(request)) {
            log.info("uri boolean : {}", isWhiteListURI(request));
            filterChain.doFilter(request, response);
        } else {
            String token = AuthUtils.getAccessToken(request);

            if (StringUtils.isNotBlank(token)) {
                Optional<Authentication> authentication = this.jwtAuthenticationProvider.getAuthentication(token);
                authentication.ifPresent(value -> SecurityContextHolder.getContext().setAuthentication(value));
            }

            if(!jwtAuthenticationProvider.validateToken(token))
                throw new BaseException(BaseResponseStatus.UNAUTHORIZED);

            filterChain.doFilter(request, response);
        }
    }

    private boolean isWhiteListURI(HttpServletRequest servletRequest) {
        return (servletRequest.getRequestURI() != null) && whiteListCheck(servletRequest.getRequestURI());
    }

    private boolean whiteListCheck(String uri){
        log.info("uri : {}", uri);
        return PatternMatchUtils.simpleMatch(whiteListUris,uri);
    }
}
