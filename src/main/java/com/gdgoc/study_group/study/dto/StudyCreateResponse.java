package com.gdgoc.study_group.study.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class StudyCreateResponse {
  private String message;
  private Long id;
}
