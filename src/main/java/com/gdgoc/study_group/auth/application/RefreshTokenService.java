package com.gdgoc.study_group.auth.application;

import com.gdgoc.study_group.auth.dao.AuthRepository;
import com.gdgoc.study_group.auth.dao.RefreshRepository;
import com.gdgoc.study_group.auth.domain.Auth;
import com.gdgoc.study_group.auth.domain.Refresh;
import com.gdgoc.study_group.auth.jwt.JwtUtil;
import io.jsonwebtoken.ExpiredJwtException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class RefreshTokenService {

    private final RefreshRepository refreshRepository;
    private final AuthRepository authRepository;
    private final JwtUtil jwtUtil;

    /**
     * 로그인 시 생성되는 리프레쉬 토큰을 데이터베이스에 저장합니다.
     *
     * @param authId 회원 정보
     * @param refreshToken 저장하려는 리프레쉬 토큰
     * @param expiredMs 저장하려는 리프레쉬 토큰의 만료 시간
     */
    @Transactional(readOnly = false)
    public void saveRefresh(Long authId, String refreshToken, Long expiredMs) {

        Auth auth = authRepository.findById(authId)
                .orElseThrow(() -> new NoSuchElementException("인증 정보를 찾을 수 없습니다."));

        // 기존 사용자의 refresh 토큰이 있다면 삭제
        refreshRepository.deleteByAuth(auth);
        refreshRepository.flush();

        Refresh refreshAuth = Refresh.builder()
                .auth(auth)
                .refresh(refreshToken)
                .expiration(new Date(System.currentTimeMillis() + expiredMs).toString())
                .build();

        refreshRepository.save(refreshAuth);
    }

    /**
     * refresh token이 유효한지 검증합니다.
     * @param refreshToken 검증할 리프레쉬 토큰
     * @return 올바르면 true (단, 유효하지 않은 토큰일 경우 false)
     */
    public boolean validateRefresh(String refreshToken) {

        try {
            if (refreshToken == null) {
                return false;
            }

            Boolean isExpired = jwtUtil.isExpired(refreshToken);
            if (isExpired) {
                return false;
            }

            String category = jwtUtil.getCategory(refreshToken);
            if (!"refresh".equals(category)) {
                return false;
            }

            Boolean isExists = refreshRepository.existsByRefresh(refreshToken);
            if (!isExists) {
                return false;
            }

            return true;

        } catch (ExpiredJwtException e) {
            return false;
        }
    }

    /**
     * 해당 리프레쉬 토큰으로 refresh 테이블에서 관련 정보를 삭제합니다.
     *
     * @param refreshToken 삭제될 리프레쉬 토큰
     */
    @Transactional(readOnly = false)
    public void deleteRefresh(String refreshToken) {
        refreshRepository.deleteByRefresh(refreshToken);
    }
}
