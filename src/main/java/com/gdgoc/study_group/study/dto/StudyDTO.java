package com.gdgoc.study_group.study.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class StudyDTO {
  private String name;

  private String description;

  @JsonProperty("isOffline")
  private boolean isOffline;

  private Integer maxParticipants;
}
