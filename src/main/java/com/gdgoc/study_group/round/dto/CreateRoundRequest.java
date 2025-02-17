package com.gdgoc.study_group.round.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.gdgoc.study_group.round.domain.RoundStatus;
import java.time.LocalDate;

public record CreateRoundRequest(
        String goal,
        String studyDetail,
        @JsonFormat(pattern = "yyyy-MM-dd")
        LocalDate date,
        RoundStatus roundStatus
) {}