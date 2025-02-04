package com.gdgoc.study_group.round.dto;

import java.time.LocalDate;
import java.util.List;

public record CreateRoundRequest(
        String goal,
        String studyDetail,
        LocalDate roundDate,
        List<RoundThumbnailDTO> thumbnails
) {}