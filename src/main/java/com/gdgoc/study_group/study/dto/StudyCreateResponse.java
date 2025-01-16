package com.gdgoc.study_group.study.dto;

import com.gdgoc.study_group.curriculum.dto.CurriculumDTO;
import com.gdgoc.study_group.day.dto.DayDTO;
import com.gdgoc.study_group.study.domain.StudyStatus;
import java.util.List;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class StudyCreateResponse {
  private Long id;
  private String name;
  private String description;
  private String requirement;
  private Integer maxParticipants;
  private StudyStatus studyStatus;
  private List<CurriculumDTO> curriculums;
  private List<DayDTO> days;
}
