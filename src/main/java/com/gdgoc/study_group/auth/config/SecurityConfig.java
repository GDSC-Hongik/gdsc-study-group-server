package com.gdgoc.study_group.auth.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gdgoc.study_group.auth.application.CookieService;
import com.gdgoc.study_group.auth.application.RefreshTokenService;
import com.gdgoc.study_group.auth.dao.RefreshRepository;
import com.gdgoc.study_group.auth.jwt.CustomLogoutFilter;
import com.gdgoc.study_group.auth.jwt.JwtFilter;
import com.gdgoc.study_group.auth.jwt.JwtUtil;
import com.gdgoc.study_group.auth.jwt.LoginFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    /**
     * 해시 방식으로 비밀번호를 암호화합니다.
     *
     * @return 암호화된 비밀번호 반환
     */
    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {

        return new BCryptPasswordEncoder();
    }

    private final AuthenticationConfiguration authenticationConfiguration;
    private final ObjectMapper objectMapper;
    private final JwtUtil jwtUtil;
    private final RefreshRepository refreshRepository;
    private final CookieService cookieService;

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {

        return configuration.getAuthenticationManager();
    }

    /**
     * 인가 작업, 로그인방식 설정, csrf 설정 등 여러 설정들을 관리합니다.
     *
     * @param http 스프링 시큐리티 설정
     * @return 설정이 완료된 security filter chain 반환
     */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http, RefreshTokenService refreshTokenService) throws Exception {

        // jwt는 stateless한 세션이기에 csrf에 대한 공격 방어 딱히 필요 없음
        http
                .csrf((auth) -> auth.disable());

        http
                .formLogin((auth) -> auth.disable());

        http
                .httpBasic((auth) -> auth.disable());

        // 경로별 인가 설정 (일단 skip)
        http
                .authorizeHttpRequests((auth) -> auth
                        .requestMatchers( "/auth/signup", "/auth/login","/", "/auth/reissue").permitAll()
                        .requestMatchers("/auth/logout").authenticated()
                        .requestMatchers("/auth").hasRole("ADMIN")
                        .anyRequest().authenticated())
                .logout((logout) -> logout.disable());

        // JwtFilter 추가
        http.addFilterBefore(
                new JwtFilter(jwtUtil),
                UsernamePasswordAuthenticationFilter.class
        );

        // LoginFilter 추가
        http.addFilterAt(
                new LoginFilter(objectMapper ,authenticationManager(authenticationConfiguration), jwtUtil, refreshTokenService, cookieService),
                UsernamePasswordAuthenticationFilter.class
        );

        // 기존 LogoutFilter 대신 CustomLogoutFilter 실행되도록 추가
        http
                .addFilterBefore(new CustomLogoutFilter(cookieService, refreshTokenService), LogoutFilter.class);

        // 세션을 stateless한 상태로 진행하기 위함
        http
                .sessionManagement((session) -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        return http.build();
    }
}