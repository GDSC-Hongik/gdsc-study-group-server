package com.gdgoc.study_group.day.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalTime;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class DayDTO {
  private String day;

  @JsonFormat(pattern = "hh:mm") // "startTime": "14:00" 형식으로 입력
  private LocalTime startTime;
}
