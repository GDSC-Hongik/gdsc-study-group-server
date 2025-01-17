package com.gdgoc.study_group.round.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDate;
import java.util.List;

public record RoundDTO(Long id, String goal, String studyDetail,
                       @JsonFormat(pattern = "yyyy-MM-dd") LocalDate roundDate,
                       List<RoundThumbnailDTO> thumbnails) {
  public RoundDTO {
    // You can add validation logic here if needed
  }
}