package com.gdgoc.study_group.study.dto;

import com.gdgoc.study_group.study.domain.Study;
import com.gdgoc.study_group.study.domain.StudyStatus;
import io.swagger.v3.oas.annotations.media.Schema;

public record StudySimpleResponse(Long id,
                                  @Schema(description = "스터디 이름") String name,
                                  @Schema(description = "스터디 설명") String description,
                                  @Schema(description = "스터디 진행방식") StudyStatus studyStatus,
                                  @Schema(description = "스터디 참여 인원") Integer participants,
                                  @Schema(description = "스터디 최대 인원") Integer maxParticipants) {
    public static StudySimpleResponse from(Study study) {
        return new StudySimpleResponse(
                study.getId(),
                study.getName(),
                study.getDescription(),
                study.getStudyStatus(),
                study.getStudyMembers().size(),
                study.getMaxParticipants());
    }
}
