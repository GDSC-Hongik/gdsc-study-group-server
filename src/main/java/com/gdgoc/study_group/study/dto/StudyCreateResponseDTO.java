package com.gdgoc.study_group.study.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StudyCreateResponseDTO {

  private String message;

  @JsonProperty("study_id")
  private Long studyId;

  public StudyCreateResponseDTO(String message, Long studyId) {
    this.message = message;
    this.studyId = studyId;
  }
}
