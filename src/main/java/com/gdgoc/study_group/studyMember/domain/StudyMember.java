package com.gdgoc.study_group.studyMember.domain;

import com.gdgoc.study_group.member.domain.Member;
import com.gdgoc.study_group.study.domain.Study;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "STUDY_MEMBER")
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

  @Builder
  public StudyMember(Member member, Study study, StudyMemberStatus studyMemberStatus) {
    this.member = member;
    this.study = study;
    this.studyMemberStatus = studyMemberStatus;
  }

  public void updateStatus(StudyMemberStatus status) {
    this.studyMemberStatus = status;
  }
}