package com.gdgoc.study_group.auth.jwt;

import com.gdgoc.study_group.auth.application.CookieService;
import com.gdgoc.study_group.auth.application.RefreshTokenService;
import com.gdgoc.study_group.auth.dao.RefreshRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.filter.GenericFilterBean;

import java.io.IOException;

@RequiredArgsConstructor
public class CustomLogoutFilter extends GenericFilterBean {

    private final JwtUtil jwtUtil;
    private final RefreshRepository refreshRepository;
    private final CookieService cookieService;
    private final RefreshTokenService refreshTokenService;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException, ServletException {

        doFilter((HttpServletRequest) request, (HttpServletResponse) response, filterChain);
    }

    /**
     * 로그아웃 관련이 아닌 부적절한 요청이라면 다음 필터로 넘깁니다.
     */
    private void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws IOException, ServletException {

        // 경로 검증
        String requestURI = request.getRequestURI();
        if (!requestURI.matches("^\\/auth\\/logout$")) {

            filterChain.doFilter(request, response);
            return;
        }

        // method 검증
        String requestMethod = request.getMethod();
        if (!requestMethod.equals("POST")) {

            filterChain.doFilter(request, response);
            return;
        }

        // 리프레쉬 토큰 검증
        String refresh = cookieService.extractCookie(request, "refresh");

        if (!refreshTokenService.validateRefresh(refresh)) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        // 검증 완료 -> 로그아웃 진행
        // 해당 리프레쉬 토큰으로 refresh 테이블에서 관련 정보 삭제
        refreshRepository.deleteByRefresh(refresh);

        // 리프레쉬 토큰의 쿠키 값 비움
        cookieService.createCookie(response, "refresh", null, 0);

        response.setStatus(HttpServletResponse.SC_OK);
    }
}

