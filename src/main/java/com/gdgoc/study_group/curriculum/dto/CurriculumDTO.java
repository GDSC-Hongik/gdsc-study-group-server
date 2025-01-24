package com.gdgoc.study_group.curriculum.dto;

import com.gdgoc.study_group.curriculum.domain.Curriculum;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

public record CurriculumDTO(
    @NotBlank(message = "스터디 주차를 입력해 주세요.") @Schema(description = "스터디 주차") Integer week,
    @NotBlank(message = "주제를 입력해 주세요.") @Schema(description = "해당 주차의 주제") String subject) {

  public static CurriculumDTO from(Curriculum curriculum) {
    return new CurriculumDTO(curriculum.getWeek(), curriculum.getSubject());
  }
}
