package com.gdgoc.study_group.member.dto.response;

public record MemberGetResponseDto(
        String studentId,
        String name,
        String github,
        String studentNumber) {}
