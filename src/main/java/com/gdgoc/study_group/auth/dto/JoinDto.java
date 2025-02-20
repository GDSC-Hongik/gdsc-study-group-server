package com.gdgoc.study_group.auth.dto;

public record JoinDto(
        String name,
        String github,
        String studentNumber,
        String password
) { }
