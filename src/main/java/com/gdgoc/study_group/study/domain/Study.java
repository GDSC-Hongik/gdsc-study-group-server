package com.gdgoc.study_group.study.domain;

import com.gdgoc.study_group.answer.domain.Answer;
import com.gdgoc.study_group.curriculum.domain.Curriculum;
import com.gdgoc.study_group.day.domain.Day;
import com.gdgoc.study_group.round.domain.Round;
import com.gdgoc.study_group.studyMember.domain.StudyMember;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import java.util.List;

@Entity
public class Study {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @OneToMany(mappedBy = "study_member_id")
  private List<StudyMember> studyMembers;

  @OneToMany(mappedBy = "round_id")
  private List<Round> rounds;

  @OneToMany(mappedBy = "curriculum_id")
  private List<Curriculum> curriculums;

  @OneToMany(mappedBy = "day_id")
  private List<Day> days;

  @OneToMany(mappedBy = "answer_id")
  private List<Answer> answers;

  private String name;
  private String description;
  private boolean isOffline;
  private boolean isActive;
  private Integer participants; // 현재 스터디 참여 인원
  private Integer maxParticipants; // null == 인원 제한 X
}
