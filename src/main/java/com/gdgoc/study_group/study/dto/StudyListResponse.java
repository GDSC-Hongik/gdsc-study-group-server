package com.gdgoc.study_group.study.dto;

import com.gdgoc.study_group.study.domain.StudyStatus;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class StudyListResponse {
  private Long id;
  private String name;
  private String description;
  private StudyStatus status;
}
