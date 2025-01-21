package com.gdgoc.study_group.study.dto;

import com.gdgoc.study_group.curriculum.dto.CurriculumDTO;
import com.gdgoc.study_group.day.dto.DayDTO;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

public record StudyResponse(
  Long id,
  @Schema(description = "스터디 이름") String name,
  @Schema(description = "스터디 설명") String description,
  @Schema(description = "스터디 지원 조건") String requirement,
  @Schema(description = "스터디 지원 질문") String question,
  @Schema(description = "스터디 커리큘럼") List<CurriculumDTO> curriculums,
  @Schema(description = "스터디 요일 및 시간") List<DayDTO> days,
  @Schema(description = "스터디 최대 인원") Integer maxParticipants,
  @Schema(description = "스터디 모집 마감 여부") boolean isApplicationClosed) {}
