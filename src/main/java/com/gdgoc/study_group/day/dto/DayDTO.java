package com.gdgoc.study_group.day.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.gdgoc.study_group.day.domain.Day;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import java.time.LocalTime;

public record DayDTO(
    @NotBlank(message = "스터디 요일을 입력해 주세요.") @Schema(description = "스터디 요일") String day,
    @JsonFormat(pattern = "HH:mm") // "startTime": "14:00" 형식으로 입력
        @NotBlank(message = "스터디 시간을 입력해 주세요")
        @Schema(description = "스터디 시간")
        LocalTime startTime) {

  public static DayDTO from(Day day) {
    return new DayDTO(day.getDay(), day.getStartTime());
  }
}
