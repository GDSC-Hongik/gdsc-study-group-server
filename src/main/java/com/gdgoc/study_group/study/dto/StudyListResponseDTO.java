package com.gdgoc.study_group.study.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL) // Null 값인 필드 제외
public class StudyListResponseDTO {
  private Long id;

  private String name;

  private String description;

  @JsonProperty("isOffline")
  private boolean isOffline;
}
