package com.gdgoc.study_group.round.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.gdgoc.study_group.round.domain.Round;
import com.gdgoc.study_group.round.domain.RoundStatus;
import java.time.LocalDate;

public record RoundResponse(
        Long roundId,
        Long studyId,
        String goal,
        String studyDetail,
        @JsonFormat(pattern = "yyyy-MM-dd")
        LocalDate date,
        RoundStatus roundStatus
) {
    public static RoundResponse from(Round round) {
        return new RoundResponse(
                round.getId(),
                round.getStudy().getId(),
                round.getGoal(),
                round.getStudyDetail(),
                round.getRoundDate(),
                round.getRoundStatus()
        );
    }
}