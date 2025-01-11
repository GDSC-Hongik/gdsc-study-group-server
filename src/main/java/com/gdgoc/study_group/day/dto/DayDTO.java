package com.gdgoc.study_group.day.dto;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DayDTO {
  private Long studyId;
  private String day;
  private LocalDateTime startTime;
}
