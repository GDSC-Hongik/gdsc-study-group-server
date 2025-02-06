package com.gdgoc.study_group.auth.application;

import com.gdgoc.study_group.auth.dao.AuthRepository;
import com.gdgoc.study_group.auth.dao.RefreshRepository;
import com.gdgoc.study_group.auth.domain.Auth;
import com.gdgoc.study_group.auth.domain.Refresh;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class RefreshTokenService {

    private final RefreshRepository refreshRepository;
    private final AuthRepository authRepository;

    /**
     * 로그인 시 생성되는 리프레쉬 토큰을 데이터베이스에 저장합니다.
     *
     * @param authId 회원 정보
     * @param refreshToken 저장하려는 리프레쉬 토큰
     * @param expiredMs 저장하려는 리프레쉬 토큰의 만료 시간
     */
    @Transactional
    public void saveRefresh(Long authId, String refreshToken, Long expiredMs) {

        Auth auth = authRepository.findById(authId)
                .orElseThrow(() -> new NoSuchElementException("인증 정보를 찾을 수 없습니다."));

        // 기존 사용자의 refresh 토큰이 있다면 삭제
        refreshRepository.deleteByAuth(auth);
        refreshRepository.flush();

        Date date = new Date(System.currentTimeMillis() + expiredMs);

        Refresh refreshAuth = Refresh.builder()
                .auth(auth)
                .refresh(refreshToken)
                .expiration(date.toString())
                .build();

        refreshRepository.save(refreshAuth);
    }
}
