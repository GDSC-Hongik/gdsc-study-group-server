package com.gdgoc.study_group.auth.jwt;

import jakarta.servlet.http.Cookie;

public class CookieUtil {

    /**
     * Utility 클래스는 인스턴스를 생성하지 않도록 private 생성자를 만듭니다.
     */
    private CookieUtil() {}

    /**
     * key 값과 value 즉 JWT 토큰을 받아와서 쿠키를 만듭니다.
     * @param key
     * @param value
     * @return
     */
    public static Cookie createCookie(String key, String value) {

        Cookie cookie = new Cookie(key, value);
        cookie.setMaxAge(24*60*60); // 쿠키 생명 주기
//        cookie.setSecure(true); // HTTPS 통신 시, 필수
        cookie.setPath("/"); // 쿠키가 적용되는 범위
        cookie.setHttpOnly(true); // 클라이언트 측의 JS로 쿠키에 접근하지 못하게 막기 (XSS 방지)

        return cookie;
    }

}
