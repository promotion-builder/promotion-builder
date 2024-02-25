package kr.njw.promotionbuilder.common.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.*;
import java.util.stream.Collectors;


@Slf4j
@Component
public class JwtAuthenticationProvider {
    private static final String AUTHORITIES_KEY = "authorities";

    private final String secret;
    private final long durationSeconds;
    private final long refreshTokenDurationSeconds;

    public JwtAuthenticationProvider(@Value("${app.security.jwt-secret}") String secret,
                                     @Value("${app.security.jwt-token-duration-seconds}") long durationSeconds,
                                     @Value("${app.security.jwt-refresh-token-duration-seconds}") long refreshTokenDurationSeconds) {
        this.secret = Base64.getEncoder().encodeToString(secret.getBytes());
        this.durationSeconds = durationSeconds;
        this.refreshTokenDurationSeconds = refreshTokenDurationSeconds;
    }

    public static SecretKey generateSecretKey(String keyString, String algorithm) {
        // Base64 디코딩
        byte[] decodedKey = Base64.getDecoder().decode(keyString);

        // SecretKeySpec을 사용하여 SecretKey 생성
        SecretKey secretKey = new SecretKeySpec(decodedKey, algorithm);

        return secretKey;
    }

    public String createRefreshToken(String username, List<Role> roles) {
        Claims claims = Jwts.claims().setSubject(username);
        claims.put(AUTHORITIES_KEY, roles.stream().map(Role::toAuthority).collect(Collectors.toList()));
        claims.setExpiration(getExpireDateRefreshToken());
        claims.setIssuedAt(new Date());

        return Jwts.builder()
                .setClaims(claims)
                .signWith(generateSecretKey(this.secret, SignatureAlgorithm.HS256.getJcaName()))
                .compact();
    }

    private Date getExpireDateRefreshToken() {
        long expireTimeMils = refreshTokenDurationSeconds * 1000L;
        return new Date(System.currentTimeMillis() + expireTimeMils);
    }

    private long getExpireDateCreateToken() {
        Date now = new Date();
        return now.getTime() + durationSeconds * 1000;
    }

    public String createToken(String username, List<Role> roles) {
        Claims claims = Jwts.claims().setSubject(username);
        claims.put(AUTHORITIES_KEY, roles.stream().map(Role::toAuthority).collect(Collectors.toList()));
        claims.setExpiration(new Date(getExpireDateCreateToken()));
        claims.setIssuedAt(new Date());

        return Jwts.builder()
                .setClaims(claims)
                .signWith(generateSecretKey(this.secret, SignatureAlgorithm.HS256.getJcaName()))
                .compact();
    }

    public Optional<Authentication> getAuthentication(String token) {
        if (!this.validateToken(token)) {
            return Optional.empty();
        }

        Claims claims = this.getClaims(token);

        if (StringUtils.isBlank(claims.getSubject())) {
            return Optional.empty();
        }

        Collection<? extends GrantedAuthority> authorities = this.getAuthorities(claims);
        UserDetails userDetails = new User(claims.getSubject(), "", authorities);

        return Optional.of(new UsernamePasswordAuthenticationToken(userDetails, "", authorities));
    }

    public boolean validateToken(String token) {
        try {
            Claims claims = this.getClaims(token);
            return claims.getExpiration().after(new Date());
        } catch (Exception e) {
            return false;
        }
    }

    public String getUsername(String token) {
        Claims claims = this.getClaims(token);
        return claims.getSubject();
    }

    private Claims getClaims(String token) {
        return Jwts.parser().setSigningKey(this.secret).parseClaimsJws(token).getBody();
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
