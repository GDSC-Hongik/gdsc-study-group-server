package com.gdgoc.study_group.auth.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
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

    /**
     * 인가 작업, 로그인방식 설정, csrf 설정 등 여러 설정들을 관리합니다.
     *
     * @param http 스프링 시큐리티 설정
     * @return 설정이 완료된 security filter chain 반환
     */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        // jwt는 stateless한 세션이기에 csrf에 대한 공격 방어 딱히 필요 없음
        http
                .csrf((auth) -> auth.disable());

        http
                .formLogin((auth) -> auth.disable());

        http
                .httpBasic((auth) -> auth.disable());

        // 경로별 인가 설정 (일단 skip)
//        http
//                .authorizeHttpRequests((auth) -> auth
//                        .requestMatchers( "/").permitAll()
//                        .requestMatchers("/users").hasRole("ADMIN")
//                        .anyRequest().authenticated());

        // 세션을 stateless한 상태로 진행하기 위함
        http
                .sessionManagement((session) -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        return http.build();
    }
}