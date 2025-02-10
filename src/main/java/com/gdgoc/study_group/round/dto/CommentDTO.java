package com.gdgoc.study_group.round.dto;

import com.gdgoc.study_group.comment.domain.Comment;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

public record CommentDTO(
    @NotNull(message = "댓글 내용을 입력해 주세요.") @Schema(description = "댓글 내용") String comment) {

    public static CommentDTO from(Comment comment) {
        return new CommentDTO(comment.getComment());
    }
}
