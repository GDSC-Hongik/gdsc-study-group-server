package com.gdgoc.study_group.round.dto;

import com.gdgoc.study_group.round.domain.RoundThumbnail;

public record RoundThumbnailDTO(
        Long id,
        String fileName,
        String filePath,
        String type
) {
  public static RoundThumbnailDTO from(RoundThumbnail thumbnail) {
    return new RoundThumbnailDTO(
            thumbnail.getId(),
            thumbnail.getFileName(),
            thumbnail.getFilePath(),
            thumbnail.getType()
    );
  }
}