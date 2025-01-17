package com.gdgoc.study_group.round.dto;

public record RoundThumbnailDTO(Long id, String fileName, String filePath, String type) {
  public RoundThumbnailDTO {
    // You can add validation logic here if needed
  }
}