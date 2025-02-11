package com.gdgoc.study_group.studyMember.domain;

import com.gdgoc.study_group.member.domain.Member;
import com.gdgoc.study_group.study.domain.Study;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "STUDY_MEMBER")
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class StudyMember {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne
  @JoinColumn(name = "member_id")
  private Member member;

  @ManyToOne
  @JoinColumn(name = "study_id")
  private Study study;

  @Enumerated(EnumType.STRING)
  private StudyMemberStatus studyMemberStatus;
  private String answer;

  //============== answer ==============//
  public void updateAnswer(String answer) {
    this.answer = answer;
  }

  //============== apply ==============//
  public void cancelApply() {
    this.studyMemberStatus = StudyMemberStatus.CANCELED;
  }
}
