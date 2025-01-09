package com.gdgoc.study_group.curriculum.domain;

import com.gdgoc.study_group.study.domain.Study;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class Curriculum {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne
  @JoinColumn(name = "study_id", nullable = false)
  private Study study;

  private Integer week;
  private String subject; // 해당 회차의 주제
}
