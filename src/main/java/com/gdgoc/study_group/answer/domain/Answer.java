package com.gdgoc.study_group.answer.domain;

import com.gdgoc.study_group.member.domain.Member;
import com.gdgoc.study_group.study.domain.Study;
import jakarta.persistence.Access;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Answer {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne
  @JoinColumn(name = "member_id")
  private Member member;

  @ManyToOne
  @JoinColumn(name = "study_id")
  private Study study;

  private String answer;

  //========= 생성자 =========//
  private Answer(Member member, Study study, String answer) {
    this.member = member;
    this.study = study;
    this.answer = answer;
  }

  /**
   * 새로운 answer 객체를 생성합니다
   * @param member 응답자
   * @param study 지원하는 스터디
   * @param answer 질문에 대한 답변
   * @return 만들어진 {@code Answer} 객체
   */
  static public Answer MakeAnswer(Member member, Study study, String answer) {
    return new Answer(member, study, answer);
  }
}
