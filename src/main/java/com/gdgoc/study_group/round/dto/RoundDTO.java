package com.gdgoc.study_group.round.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.gdgoc.study_group.round.domain.Round;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

public record RoundDTO(
        Long id,
        String goal,
        String studyDetail,
        @JsonFormat(pattern = "yyyy-MM-dd")
        LocalDate roundDate,
        List<RoundThumbnailDTO> thumbnails
) {
  public static RoundDTO from(Round round) {
    return new RoundDTO(
            round.getId(),
            round.getGoal(),
            round.getStudyDetail(),
            round.getRoundDate(),
            round.getImages().stream()
                    .map(RoundThumbnailDTO::from)
                    .collect(Collectors.toList())
    );
  }
}