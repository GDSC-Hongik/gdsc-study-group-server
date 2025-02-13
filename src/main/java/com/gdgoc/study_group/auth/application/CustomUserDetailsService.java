package com.gdgoc.study_group.auth.application;

import com.gdgoc.study_group.auth.dao.AuthRepository;
import com.gdgoc.study_group.auth.domain.Auth;
import com.gdgoc.study_group.auth.dto.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final AuthRepository authRepository;

    /**
     * 데이터베이스에서 학번으로 회원을 조회하여 반환합니다.
     * @param studentNumber 조회할 대상 정보
     * @return 조회된 회원 정보를 UserDetails 형식으로 반환
     */
    @Override
    public UserDetails loadUserByUsername(String studentNumber) throws UsernameNotFoundException {

        Auth userData = authRepository.findByMember_StudentNumber(studentNumber);

        if (userData == null) {
            throw new UsernameNotFoundException("해당 학번의 사용자를 찾을 수 없습니다");
        }

        return new CustomUserDetails(userData);
    }
}