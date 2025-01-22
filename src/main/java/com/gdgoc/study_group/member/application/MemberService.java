package com.gdgoc.study_group.member.application;

import com.gdgoc.study_group.member.dao.MemberRepository;
import com.gdgoc.study_group.member.domain.Member;
import com.gdgoc.study_group.member.dto.request.MemberCreateRequestDto;
import com.gdgoc.study_group.member.dto.request.MemberUpdateRequestDto;
import java.util.NoSuchElementException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MemberService {

  private final MemberRepository memberRepository;

  /**
   * 새 멤버를 생성합니다.
   *
   * @param request 새로 만들 멤버 정보
   * @return Member 형태로 반환
   */
  @Transactional
  public Member createMember(MemberCreateRequestDto request) {

    Member newMember =
        Member.builder()
            .name(request.getName())
            .github(request.getGithub())
            .studentNumber(request.getStudentNumber())
            .build();

    return memberRepository.save(newMember);
  }

  /**
   * 멤버를 조회합니다.
   *
   * @param memberId 멤버를 조회할 정보
   * @return Member 형태로 반환
   */
  @Transactional(readOnly = true)
  public Member getMember(Long memberId) {
    return memberRepository
        .findById(memberId)
        .orElseThrow(() -> new NoSuchElementException("member not found"));
  }

  /**
   * 멤버가 정보를 수정합니다.
   *
   * @param memberId 멤버를 조회할 정보
   * @param request 수정할 정보
   */
  @Transactional
  public void updateMember(Long memberId, MemberUpdateRequestDto request) {

    Member member = getMember(memberId);

    member.updateMember(request.getName(), request.getGithub(), request.getStudentNumber());
  }
}
