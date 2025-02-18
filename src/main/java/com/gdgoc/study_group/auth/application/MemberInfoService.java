package com.gdgoc.study_group.auth.application;

import com.gdgoc.study_group.member.dao.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberInfoService {

    private final MemberRepository memberRepository;

    /**
     * 현재 로그인한 사용자의 토큰에서 학번 정보를 추출하여 고유 id를 반환합니다.
     *
     * @return 현재 로그인한 사용자의 고유 id
     */
    public Long getMemberId() {

        String studentNumber = SecurityContextHolder.getContext().getAuthentication().getName();

        System.out.println(memberRepository.findByStudentNumber(studentNumber));
        return memberRepository.findByStudentNumber(studentNumber).getId();
    }
}
