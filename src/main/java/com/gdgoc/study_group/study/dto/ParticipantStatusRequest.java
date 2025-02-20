package com.gdgoc.study_group.study.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.gdgoc.study_group.studyMember.domain.StudyMemberStatus;

public record ParticipantStatusRequest(String name, @JsonProperty("student_number") String studentNumber,
                                       String github, StudyMemberStatus status) {
}
