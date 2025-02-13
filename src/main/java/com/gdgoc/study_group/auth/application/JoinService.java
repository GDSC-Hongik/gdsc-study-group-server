package com.gdgoc.study_group.auth.application;

import com.gdgoc.study_group.auth.dao.AuthRepository;
import com.gdgoc.study_group.auth.domain.Auth;
import com.gdgoc.study_group.auth.dto.JoinDto;
import com.gdgoc.study_group.member.dao.MemberRepository;
import com.gdgoc.study_group.member.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class JoinService {

    private final AuthRepository authRepository;
    private final MemberRepository memberRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    /**
     * 학번과 비밀번호 등의 정보로 회원 가입을 합니다.
     *
     * @param joinDto 회원 가입에 필요한 데이터 dto
     */
    @Transactional(readOnly = false)
    public void joinMember(JoinDto joinDto) {

        String studentNumber = joinDto.studentNumber();

        if (authRepository.existsByMember_StudentNumber(studentNumber)) {
            throw new IllegalArgumentException("이미 존재하는 학번입니다.");
        }

        Member member = Member.builder()
                .name(joinDto.name())
                .github(joinDto.github())
                .studentNumber(joinDto.studentNumber())
                .build();

        member = memberRepository.save(member);

        Auth auth = Auth.builder()
                .member(member)
                .password(bCryptPasswordEncoder.encode(joinDto.password()))
                .role("ROLE_USER")
                .build();

        authRepository.save(auth);
    }
}