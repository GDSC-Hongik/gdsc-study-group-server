package com.gdgoc.study_group.curriculum.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CurriculumDTO {
  private Integer week;
  private String subject;
}
