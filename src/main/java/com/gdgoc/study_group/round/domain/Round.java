package com.gdgoc.study_group.round.domain;

import com.gdgoc.study_group.comment.domain.Comment;
import com.gdgoc.study_group.roundMember.domain.RoundMember;
import com.gdgoc.study_group.study.domain.Study;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Round {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne
  @JoinColumn(name = "study_id")
  private Study study;

  @OneToMany(mappedBy = "round")
  private List<RoundMember> roundMembers = new ArrayList<>();

  @OneToMany(mappedBy = "round")
  private List<RoundThumbnail> images = new ArrayList<>();

  @OneToMany(mappedBy = "round")
  private List<Comment> comments = new ArrayList<>();

  private String goal; // 학습 목표
  private String studyDetail; // 학습 내용
  private LocalDate roundDate; // 회차 진행한 날짜
}
