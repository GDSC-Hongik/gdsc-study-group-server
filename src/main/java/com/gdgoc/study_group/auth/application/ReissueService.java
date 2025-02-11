package com.gdgoc.study_group.auth.application;

import com.gdgoc.study_group.auth.jwt.JwtUtil;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReissueService {
    private final JwtUtil jwtUtil;

    public ResponseEntity<?> reissueToken(HttpServletRequest request, HttpServletResponse response) {

        String refresh = null;

        /**
         * 요청에서 refresh 쿠키를 찾아서,
         * 해당 값의 null 여부를 확인합니다.
         */
        Cookie[] cookies = request.getCookies();

        for (Cookie cookie : cookies) {
            if (cookie.getName().equals("refresh")) {
                refresh = cookie.getValue();
            }
        }

        if (refresh == null) {
            return new ResponseEntity<>("refresh token null", HttpStatus.BAD_REQUEST);
        }

        /**
         * refresh 쿠키 존재 시,
         * 만료 여부와 category를 확인합니다.
         */
        try {
            jwtUtil.isExpired(refresh);
        } catch (ExpiredJwtException e) {
            return new ResponseEntity<>("refresh token expired", HttpStatus.BAD_REQUEST);
        }

        String category = jwtUtil.getCategory(refresh);
        if (!category.equals("refresh")) {
            return new ResponseEntity<>("invalid refresh token", HttpStatus.BAD_REQUEST);
        }

        /**
         * refresh token 검증을 마치면,
         * 새로운 access token을 발급합니다.
         */
        Long authId = jwtUtil.getAuthId(refresh);
        String studentNumber = jwtUtil.getStudentNumber(refresh);
        String role = jwtUtil.getRole(refresh);

        String newAccess = jwtUtil.createJWT("access", authId, studentNumber, role, 600000L);

        // 응답
        response.setHeader("Authorization", "Bearer " + newAccess);

        return new ResponseEntity<>(HttpStatus.OK);
    }

}
