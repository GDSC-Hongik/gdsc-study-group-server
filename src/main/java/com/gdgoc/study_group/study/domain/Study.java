package com.gdgoc.study_group.study.domain;

import com.gdgoc.study_group.answer.domain.Answer;
import com.gdgoc.study_group.curriculum.domain.Curriculum;
import com.gdgoc.study_group.curriculum.dto.CurriculumDTO;
import com.gdgoc.study_group.day.domain.Day;
import com.gdgoc.study_group.day.dto.DayDTO;
import com.gdgoc.study_group.member.domain.Member;
import com.gdgoc.study_group.round.domain.Round;
import com.gdgoc.study_group.studyMember.domain.StudyMember;
import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;
import lombok.*;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Study {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @OneToMany(mappedBy = "study", cascade = CascadeType.PERSIST)
  private List<StudyMember> studyMembers = new ArrayList<>();

  @OneToMany(mappedBy = "study")
  private List<Round> rounds = new ArrayList<>();

  @OneToMany(mappedBy = "study", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<Curriculum> curriculums = new ArrayList<>();

  @OneToMany(mappedBy = "study", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<Day> days = new ArrayList<>();

  @OneToMany(mappedBy = "study", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<Answer> answers = new ArrayList<>();

  @Enumerated(EnumType.STRING)
  private StudyStatus studyStatus;

  private String name;
  private String description;
  private String requirement; // 지원 자격, nullable: 별도 요구 자격 없음
  private String question; // 지원 질문, nullable: 지원 답변 없이 바로 신청 가능
  private Integer maxParticipants; // null == 인원 제한 X
  private Boolean isApplicationClosed = false; // 멤버 지원 종료 여부(기본값은 지원 가능)

  public Answer addAnswer(Member member, String answer) {
    Answer madeAns = Answer.MakeAnswer(member, this, answer);
    answers.add(madeAns);
    return madeAns;
  }
  public void clearAnswer() {
    answers.clear();
  }

  public void addInfo(List<CurriculumDTO> curriculumDTOs, List<DayDTO> dayDTOs) {
    // 등록된 커리큘럼이 있다면 엔티티로 변환하여 리스트에 추가
    this.curriculums =
        curriculumDTOs.stream()
            .map(
                curriculumDTO ->
                    Curriculum.create(this, curriculumDTO.week(), curriculumDTO.subject()))
            .toList();

    // 등록된 스터디 날짜가 있다면 엔티티로 변환하여 리스트에 추가
    this.days =
        dayDTOs.stream().map(dayDTO -> Day.create(this, dayDTO.day(), dayDTO.startTime())).toList();
  }

  public void update(
      String name,
      String description,
      String requirement,
      String question,
      Integer maxParticipants,
      StudyStatus studyStatus,
      List<CurriculumDTO> curriculumDTOs,
      List<DayDTO> dayDTOs) {
    this.name = name;
    this.description = description;
    this.requirement = requirement;
    this.question = question;
    this.maxParticipants = maxParticipants;
    this.studyStatus = studyStatus;

    this.getCurriculums().clear();
    this.getCurriculums()
        .addAll(
            curriculumDTOs.stream()
                .map(
                    curriculumDTO ->
                        Curriculum.create(this, curriculumDTO.week(), curriculumDTO.subject()))
                .toList());

    this.getDays().clear();
    this.getDays()
        .addAll(
            dayDTOs.stream()
                .map(dayDTO -> Day.create(this, dayDTO.day(), dayDTO.startTime()))
                .toList());
  }
}
