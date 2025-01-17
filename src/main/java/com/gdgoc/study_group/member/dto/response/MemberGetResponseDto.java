package com.gdgoc.study_group.member.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class MemberGetResponseDto {
    private String studentId;
    private String name;
    private String github;
    private String studentNumber;
}
