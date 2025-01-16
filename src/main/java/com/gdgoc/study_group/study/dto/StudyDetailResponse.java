package com.gdgoc.study_group.study.dto;

import com.gdgoc.study_group.curriculum.dto.CurriculumDTO;
import com.gdgoc.study_group.day.dto.DayDTO;
import java.util.List;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class StudyDetailResponse {
  private Long id;
  private String name;
  private String requirement;
  private String description;
  private String question;
  private List<CurriculumDTO> curriculums;
  private List<DayDTO> days;
  private Integer maxParticipants;
  private boolean isApplicationClosed;
}
