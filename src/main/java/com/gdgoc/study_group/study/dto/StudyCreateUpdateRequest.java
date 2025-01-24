package com.gdgoc.study_group.study.dto;

import com.gdgoc.study_group.curriculum.dto.CurriculumDTO;
import com.gdgoc.study_group.day.dto.DayDTO;
import com.gdgoc.study_group.study.domain.StudyStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.util.List;

public record StudyCreateRequest(
    @NotBlank(message = "스터디 이름을 입력해 주세요.") @Schema(description = "스터디 이름") String name,
    @NotBlank(message = "스터디 소개를 입력해 주세요.") @Schema(description = "스터디 소개") String description,
    @Schema(description = "스터디 자원 자격") String requirement,
    @Schema(description = "스터디 지원 질문") String question,
    @Schema(description = "스터디 최대 인원") Integer maxParticipants,
    @Schema(description = "스터디 커리큘럼") List<CurriculumDTO> curriculums,
    @Schema(description = "스터디 요일 및 시간") List<DayDTO> days,
    @NotNull(message = "모임 방식을 선택해 주세요.") @Schema(description = "모임 방식(온/오프라인)")
        StudyStatus studyStatus) {}
