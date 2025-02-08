package com.gdgoc.study_group.member.application;

import com.gdgoc.study_group.member.dao.MemberRepository;
import com.gdgoc.study_group.member.domain.Member;
import java.util.NoSuchElementException;
import java.util.Optional;
import com.gdgoc.study_group.member.dto.request.MemberRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberService {

  private final MemberRepository memberRepository;

  /**
   * 새 멤버를 생성합니다.
   *
   * @param request 새로 만들 멤버 정보
   * @return Member 형태로 반환
   */
  @Transactional
  public Member createMember(MemberRequestDto request) {

    Member newMember =
        Member.builder()
            .name(request.name())
            .github(request.github())
            .studentNumber(request.studentNumber())
            .build();

    return memberRepository.save(newMember);
  }

  /**
   * 멤버를 조회합니다.
   *
   * @param memberId 멤버를 조회할 정보
   * @return Member 형태로 반환
   */
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
  public void updateMember(Long memberId, MemberRequestDto request) {

    Member member = getMember(memberId);

    // 수정할 정보가 있을 시, dto에 담겨온 정보로 수정됨
    String updatedName = Optional.ofNullable(request.name())
            .orElse(member.getName());
    String updatedGithub = Optional.ofNullable(request.github())
            .orElse(member.getGithub());
    String updatedStudentNumber = Optional.ofNullable(request.studentNumber())
            .orElse(member.getStudentNumber());

    member.updateMember(updatedName, updatedGithub, updatedStudentNumber);
  }
}
