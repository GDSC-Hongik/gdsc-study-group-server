package com.gdgoc.study_group.auth.application;

import com.gdgoc.study_group.auth.jwt.JwtUtil;
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
    private final CookieService cookieService;
    private final RefreshTokenService refreshTokenService;

    public ResponseEntity<?> reissueToken(HttpServletRequest request, HttpServletResponse response) {

        // 요청에서 refresh 쿠키를 추출하는 메서드 호출
        String refresh = cookieService.extractCookie(request, "refresh");

        // refresh 토큰을 검증하는 메서드 호출
        if (!refreshTokenService.validateRefresh(refresh)) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        // 새로운 access token 발급
        Long authId = jwtUtil.getAuthId(refresh);
        String studentNumber = jwtUtil.getStudentNumber(refresh);
        String role = jwtUtil.getRole(refresh);

        String newAccess = jwtUtil.createJWT("access", authId, studentNumber, role, 600000L);

        // 응답
        response.setHeader("Authorization", "Bearer " + newAccess);

        return new ResponseEntity<>(HttpStatus.OK);
    }

}
