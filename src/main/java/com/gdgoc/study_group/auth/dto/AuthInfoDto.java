package com.gdgoc.study_group.auth.dto;

import lombok.Builder;

@Builder
public record AuthInfoDto(
        String studentNumber,
        String role,
        String password
) {
    public AuthInfoDto {
        password = password != null ? password : "";
    }
}
