package com.gdgoc.study_group.auth.application;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Service;

@Service
public class CookieService {

    /**
     * key 값과 value 즉 JWT 토큰을 받아와서 새로운 쿠키를 생성하고 HTTP 응답에 추가합니다.
     * @param response HTTP 응답
     * @param key 쿠키의 키
     * @param value 쿠키의 값
     * @param expiredS 쿠키 만료 시간
     */
    public void createCookie(HttpServletResponse response, String key, String value, Integer expiredS) {
        Cookie cookie = new Cookie(key, value);
        cookie.setMaxAge(expiredS); // 쿠키 생명 주기
        cookie.setPath("/"); // 쿠키가 적용되는 범위
        cookie.setHttpOnly(true); // 클라이언트 측의 JS로 쿠키에 접근하지 못하게 막기 (XSS 방지)
//        cookie.setSecure(true); // HTTPS 통신 시, 필수

        response.addCookie(cookie);
    }
}