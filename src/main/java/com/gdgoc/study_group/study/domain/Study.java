package com.gdgoc.study_group.study.domain;

import com.gdgoc.study_group.answer.domain.Answer;
import com.gdgoc.study_group.curriculum.domain.Curriculum;
import com.gdgoc.study_group.day.domain.Day;
import com.gdgoc.study_group.round.domain.Round;
import com.gdgoc.study_group.studyMember.domain.StudyMember;
import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;
import lombok.*;

@Entity
@Builder
@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor
public class Study {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @OneToMany(mappedBy = "study")
  private List<StudyMember> studyMembers = new ArrayList<>();

  @OneToMany(mappedBy = "study")
  private List<Round> rounds = new ArrayList<>();

  @OneToMany(mappedBy = "study", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<Curriculum> curriculums = new ArrayList<>();

  @OneToMany(mappedBy = "study", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<Day> days = new ArrayList<>();

  @OneToMany(mappedBy = "study")
  private List<Answer> answers = new ArrayList<>();

  @Enumerated(EnumType.STRING)
  private StudyStatus studyStatus;

  private String name;
  private String description;
  private String requirement; // 지원 자격, nullable: 별도 요구 자격 없음
  private String question; // 지원 질문, nullable: 지원 답변 없이 바로 신청 가능
  private Integer maxParticipants; // null == 인원 제한 X
  private Boolean isApplicationClosed = false; // 멤버 지원 종료 여부(기본값은 지원 가능)
}
