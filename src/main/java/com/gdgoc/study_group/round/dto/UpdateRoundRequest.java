package com.gdgoc.study_group.round.dto;

import com.gdgoc.study_group.round.domain.RoundStatus;

import java.time.LocalDate;
import java.util.List;

public record UpdateRoundRequest(
        String goal,
        String studyDetail,
        LocalDate roundDate,
        RoundStatus roundStatus,
        List<RoundThumbnailDTO> thumbnails
) {}
