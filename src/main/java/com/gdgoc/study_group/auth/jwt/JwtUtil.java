package com.gdgoc.study_group.auth.jwt;

import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Component
public class JwtUtil {
    /* secret key 생성 */
    private SecretKey secretKey;

    public JwtUtil(@Value("${SECRET_KEY}")String secret) {

        this.secretKey = new SecretKeySpec(secret.getBytes(StandardCharsets.UTF_8), Jwts.SIG.HS256.key().build().getAlgorithm());
    }

    /* 검증 */
    public String getCategory(String token) {
        // category 검증

        return Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).getPayload().get("category", String.class);
        // return 값으로 category 값이 반환됨

    }

    public Long getAuthId(String token) {
        // authId 검증

        return Jwts.parser()
                .verifyWith(secretKey)
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .get("authId", Long.class);
    }

    public String getStudentNumber(String token) {
        // studentNumber 검증

        return Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).getPayload().get("studentNumber", String.class);
    }

    public String getRole(String token) {
        // password 검증

        return Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).getPayload().get("role", String.class);
    }

    public Boolean isExpired(String token) {
        // 토큰 유효 검증

        return Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).getPayload().getExpiration().before(new Date());
    }

    /* 토큰 생성 */
    public String createJWT(String category, Long authId, String studentNumber, String role, Long expiredMs) {

        return Jwts.builder()
                .claim("category", category) // access, refresh token 구분
                .claim("authId", authId)
                .claim("studentNumber", studentNumber)
                .claim("role", role)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + expiredMs))
                .signWith(secretKey)
                .compact();
    }
}
