package com.gdgoc.study_group.auth.dto;

import com.gdgoc.study_group.auth.domain.Auth;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import java.util.ArrayList;
import java.util.Collection;

/**
 * Spring Security의 UserDetails 인터페이스를 구현한 클래스로,
 * 로그인 후 인증된 사용자 정보를 담고 있습니다.
 */
@RequiredArgsConstructor
public class CustomUserDetails implements UserDetails {

    private final AuthInfoDto authInfoDto;

    public CustomUserDetails(Auth auth) {
        this.authInfoDto = AuthInfoDto.builder()
                .authId(auth.getId())
                .studentNumber(auth.getMember().getStudentNumber())
                .password(auth.getPassword())
                .role(auth.getRole())
                .build();
    }

    /* UserDetails 관련 메소드들 */
    public Long getAuthId() {
        return authInfoDto.authId();
    }

    @Override
    public String getUsername() {
        return authInfoDto.studentNumber();
    }

    @Override
    public String getPassword() {
        return authInfoDto.password();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // 회원 역할 반환

        Collection<GrantedAuthority> collection = new ArrayList<>();

        collection.add(new GrantedAuthority() {

            @Override
            public String getAuthority() {

                return authInfoDto.role();
            }
        });

        return collection;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}

