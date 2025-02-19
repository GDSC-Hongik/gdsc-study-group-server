package com.gdgoc.study_group.comment.dto;

import com.gdgoc.study_group.comment.domain.Comment;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

public record CommentRequest(
    @NotNull(message = "댓글 내용을 입력해 주세요.") @Schema(description = "댓글 내용") String comment) { }
