package kr.njw.promotionbuilder.common.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.time.Instant;
import java.util.*;
import java.util.stream.Collectors;


@Slf4j
@Component
public class JwtAuthenticationProvider {
    private static final String AUTHORITIES_KEY = "authorities";

    private final String secret;
    private final long accessTokenDurationSeconds;
    private final long refreshTokenDurationSeconds;

    public JwtAuthenticationProvider(@Value("${app.security.jwt-secret}") String secret,
                                     @Value("${app.security.jwt-access-token-duration-seconds}") long accessTokenDurationSeconds,
                                     @Value("${app.security.jwt-refresh-token-duration-seconds}") long refreshTokenDurationSeconds) {
        this.secret = Base64.getEncoder().encodeToString(secret.getBytes());
        this.accessTokenDurationSeconds = accessTokenDurationSeconds;
        this.refreshTokenDurationSeconds = refreshTokenDurationSeconds;
    }

    public static SecretKey generateSecretKey(String keyString, String algorithm) {
        // Base64 디코딩
        byte[] decodedKey = Base64.getDecoder().decode(keyString);

        // SecretKeySpec을 사용하여 SecretKey 생성
        return new SecretKeySpec(decodedKey, algorithm);
    }

    public String createAccessToken(Long userId, List<Role> roles) {
        Instant now = Instant.now();

        Claims claims = Jwts.claims().setSubject(String.valueOf(userId));
        claims.put(AUTHORITIES_KEY, roles.stream().map(Role::toAuthority).collect(Collectors.toList()));
        claims.setExpiration(Date.from(now.plusSeconds(this.accessTokenDurationSeconds)));
        claims.setIssuedAt(Date.from(now));

        return Jwts.builder()
                .setClaims(claims)
                .signWith(generateSecretKey(this.secret, SignatureAlgorithm.HS256.getJcaName()))
                .compact();
    }

    public String createRefreshToken(Long userId, List<Role> roles) {
        Instant now = Instant.now();

        Claims claims = Jwts.claims().setSubject(String.valueOf(userId));
        claims.put(AUTHORITIES_KEY, roles.stream().map(Role::toAuthority).collect(Collectors.toList()));
        claims.setExpiration(Date.from(now.plusSeconds(this.refreshTokenDurationSeconds)));
        claims.setIssuedAt(Date.from(now));

        return Jwts.builder()
                .setClaims(claims)
                .signWith(generateSecretKey(this.secret, SignatureAlgorithm.HS256.getJcaName()))
                .compact();
    }

    public Optional<UsernamePasswordAuthenticationToken> getAuthentication(String token) {
        if (!this.validateToken(token)) {
            return Optional.empty();
        }

        Claims claims = this.getClaims(token);

        if (claims == null || StringUtils.isBlank(claims.getSubject())) {
            return Optional.empty();
        }

        Collection<? extends GrantedAuthority> authorities = this.getAuthorities(claims);
        UserDetails userDetails = new User(claims.getSubject(), "", authorities);

        return Optional.of(new UsernamePasswordAuthenticationToken(userDetails, "", authorities));
    }

    public Optional<Long> getUserId(String token) {
        Claims claims = this.getClaims(token);

        if (claims == null) {
            return Optional.empty();
        }

        return Optional.of(Long.valueOf(claims.getSubject()));
    }

    public boolean validateToken(String token) {
        Instant now = Instant.now();

        try {
            Claims claims = this.getClaims(token);

            if (claims == null) {
                return false;
            }

            return claims.getExpiration().after(Date.from(now));
        } catch (Exception e) {
            return false;
        }
    }

    private Claims getClaims(String token) {
        try {
            return Jwts.parser().setSigningKey(this.secret).parseClaimsJws(token).getBody();
        } catch (Exception e) {
            return null;
        }
    }

    private Collection<? extends GrantedAuthority> getAuthorities(Claims claims) {
        Object authorities = claims.get(AUTHORITIES_KEY);

        if (authorities instanceof Collection<?> authorityCollection) {
            return authorityCollection.stream()
                    .map(Object::toString)
                    .map(SimpleGrantedAuthority::new)
                    .collect(Collectors.toList());
        }

        return Collections.emptyList();
    }
}
