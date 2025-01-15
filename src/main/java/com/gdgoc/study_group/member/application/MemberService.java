package com.gdgoc.study_group.member.application;

import com.gdgoc.study_group.member.dao.MemberRepository;
import com.gdgoc.study_group.member.domain.Member;
import com.gdgoc.study_group.member.dto.MemberCreateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

//    public MemberService(MemberRepository memberRepository) {
//        this.memberRepository = memberRepository;
//    }

    /**
     * 멤버를 생성합니다.
     *
     * @param request 생성할 멤버의 정보
     * @return 생성된 정보를 Member 형식으로 반환
     */
    public Member createMember(MemberCreateRequestDto request) {

        Member newMember = Member.builder()
                .name(request.getName())
                .github(request.getGithub())
                .studentNumber(request.getStudentNumber())
                .build();

        return memberRepository.save(newMember);
    }
}
