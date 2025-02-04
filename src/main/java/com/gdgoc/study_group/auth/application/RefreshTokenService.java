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

    @Transactional
    public void saveRefresh(Long authId, String refreshToken, Long expiredMs) {

        Auth auth = authRepository.findById(authId)
                .orElseThrow(() -> new NoSuchElementException("인증 정보를 찾을 수 없습니다."));

        Date date = new Date(System.currentTimeMillis() + expiredMs);

        Refresh refreshAuth = Refresh.builder()
                .auth(auth)
                .refresh(refreshToken)
                .expiration(date.toString())
                .build();

        refreshRepository.save(refreshAuth);
    }
}
