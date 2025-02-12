package com.gdgoc.study_group.study.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.gdgoc.study_group.member.domain.Member;
import com.gdgoc.study_group.studyMember.domain.StudyMember;
import com.gdgoc.study_group.studyMember.domain.StudyMemberStatus;

public record StudyParticipantResponse(String name, @JsonProperty("student_number") String studentNumber,
                                       String github, StudyMemberStatus status) {
    public static StudyParticipantResponse from(StudyMember studyMember) {
        Member member = studyMember.getMember();
        return new StudyParticipantResponse(
                member.getName(),
                member.getStudentNumber(),
                member.getGithub(),
                studyMember.getStudyMemberStatus()
        );
    }
}
