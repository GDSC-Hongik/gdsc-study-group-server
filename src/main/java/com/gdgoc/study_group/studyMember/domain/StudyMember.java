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

@Entity
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
  private Status status;
}
