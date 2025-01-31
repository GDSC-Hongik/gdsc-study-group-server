package com.gdgoc.study_group.auth.jwt;

import com.gdgoc.study_group.auth.dto.AuthInfoDto;
import com.gdgoc.study_group.auth.dto.CustomUserDetails;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

/**
 * JWT 인증 처리 필터
 * : JWT 검증 후 인증 정보를 세션에 설정
 */
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        /* 헤더 검증 */
        String authorization = request.getHeader("Authorization");

        if (authorization == null || !authorization.startsWith("Bearer ")) {
            // 헤더 인증 정보에 문제가 있다면, 다음 필터로 넘김

            filterChain.doFilter(request, response);
            return;
        }

        /* 순수 토큰 검증 */
        String token = authorization.split(" ")[1];

        System.out.println("JWT Token: " + token);

        if (jwtUtil.isExpired(token)) {
            // 토큰 만료 여부 확인

            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }

        String category = jwtUtil.getCategory(token);

        if (!category.equals("access")) {
            // 토큰의 category가 access인지 확인

            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;

        }

        /* 세션에 잠깐 담아둘 정보 관리 */
        // 토큰에서 사용자 정보 추출
        String studentNumber = jwtUtil.getStudentNumber(token);
        String role = jwtUtil.getRole(token);

        // 사용자 정보 DTO 생성
        AuthInfoDto authInfoDto = AuthInfoDto.builder()
                .studentNumber(studentNumber)
                .role(role)
                .build();

        CustomUserDetails customUserDetails = new CustomUserDetails(authInfoDto);

        // Spring Security에 등록될 인증 토큰 생성
        Authentication authToken = new UsernamePasswordAuthenticationToken(customUserDetails, null, customUserDetails.getAuthorities());

        // 세션에 회원 등록
        SecurityContextHolder.getContext().setAuthentication(authToken);

        // 모든게 완료됐으니, 다음 필터로 넘김
        filterChain.doFilter(request, response);

    }

    }
