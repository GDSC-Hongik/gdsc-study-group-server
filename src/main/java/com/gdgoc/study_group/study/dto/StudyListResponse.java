package com.gdgoc.study_group.study.dto;

import com.gdgoc.study_group.study.domain.StudyStatus;
import io.swagger.v3.oas.annotations.media.Schema;

public record StudyListResponse (
  Long id,
  @Schema(description = "스터디 이름") String name,
  @Schema(description = "스터디 설명") String description,
  @Schema(description = "모임 방식(온/오프라인)") StudyStatus status) {}
