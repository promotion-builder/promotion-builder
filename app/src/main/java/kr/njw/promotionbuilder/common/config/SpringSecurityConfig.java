package kr.njw.promotionbuilder.common.config;

import kr.njw.promotionbuilder.common.security.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.CorsUtils;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@RequiredArgsConstructor
@Configuration
public class SpringSecurityConfig {
    public static final String[] PUBLIC_URIS = new String[]{
            "/api/auth/**",
            "/api/users",
            "/api/users/username",
            "/api/samples/**",
            "/user/**",
            "/sample/**"
    };

    @Value("${app.security.master-api-key}")
    private final String MASTER_API_KEY;

    private final JwtAuthenticationProvider jwtAuthenticationProvider;
    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    private final JwtAccessDeniedHandler jwtAccessDeniedHandler;

    @Bean
    @Order(1)
    public SecurityFilterChain adminSecurityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .securityMatcher("/v3/api-docs*/**", "/swagger-ui*/**", "/actuator/**");

        httpSecurity
                .authorizeHttpRequests()
                .anyRequest().hasRole(Role.ADMIN.getValue());

        httpSecurity
                .httpBasic().and()
                .formLogin().disable()
                .rememberMe().disable()
                .logout().disable()
                .csrf().disable()
                .cors().and()
                .headers().defaultsDisabled().cacheControl().and().and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        return httpSecurity.build();
    }

    @Bean
    @Order(2)
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .authorizeHttpRequests()
                .requestMatchers(CorsUtils::isPreFlightRequest).permitAll()
                .requestMatchers(PUBLIC_URIS).permitAll()
                .anyRequest().hasAnyRole(Role.USER.getValue(), Role.ADMIN.getValue());

        httpSecurity
                .httpBasic().disable()
                .formLogin().disable()
                .rememberMe().disable()
                .logout().disable()
                .csrf().disable()
                .cors().and()
                .headers().defaultsDisabled().cacheControl().and().and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
                .exceptionHandling()
                .authenticationEntryPoint(this.jwtAuthenticationEntryPoint)
                .accessDeniedHandler(this.jwtAccessDeniedHandler).and()
                .addFilterBefore(new JwtAuthenticationFilter(this.jwtAuthenticationProvider), UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(new MasterAuthenticationFilter(this.MASTER_API_KEY), UsernamePasswordAuthenticationFilter.class);

        return httpSecurity.build();
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.ignoring().requestMatchers("/h2-console/**", "/actuator/health/**");
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.setAllowedOriginPatterns(List.of("*"));
        corsConfiguration.setAllowedMethods(List.of("*"));
        corsConfiguration.setAllowedHeaders(List.of("*"));
        corsConfiguration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource urlBasedCorsConfigurationSource = new UrlBasedCorsConfigurationSource();
        urlBasedCorsConfigurationSource.registerCorsConfiguration("/**", corsConfiguration);

        return urlBasedCorsConfigurationSource;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }
}
