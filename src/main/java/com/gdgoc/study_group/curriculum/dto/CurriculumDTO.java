package com.gdgoc.study_group.curriculum.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CurriculumDTO {
  private Long studyId;
  private Integer week;
  private String subject;
}
