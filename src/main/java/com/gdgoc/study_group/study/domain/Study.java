package com.gdgoc.study_group.study.domain;

import com.gdgoc.study_group.answer.domain.Answer;
import com.gdgoc.study_group.curriculum.domain.Curriculum;
import com.gdgoc.study_group.day.domain.Day;
import com.gdgoc.study_group.round.domain.Round;
import com.gdgoc.study_group.studyMember.domain.StudyMember;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Study {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @OneToMany(mappedBy = "study")
  private List<StudyMember> studyMembers = new ArrayList<>();

  @OneToMany(mappedBy = "study")
  private List<Round> rounds = new ArrayList<>();

  @OneToMany(mappedBy = "study")
  private List<Curriculum> curriculums = new ArrayList<>();

  @OneToMany(mappedBy = "study")
  private List<Day> days = new ArrayList<>();

  @OneToMany(mappedBy = "study")
  private List<Answer> answers = new ArrayList<>();

  @Enumerated(EnumType.STRING)
  private Status status;

  private String name;
  private String description;
  private String requirement; // 지원 자격, nullable: 별도 요구 자격 없음
  private String question; // 지원 질문, nullable: 지원 답변 없이 바로 신청 가능
  private Integer maxParticipants; // null == 인원 제한 X
}
