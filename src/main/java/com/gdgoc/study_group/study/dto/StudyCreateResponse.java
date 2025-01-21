package com.gdgoc.study_group.study.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

public record StudyCreateResponse(
        @NotNull(message = "id는 null이 될 수 없습니다.") @Schema(description = "생성된 스터디의 id") Long id) {}
