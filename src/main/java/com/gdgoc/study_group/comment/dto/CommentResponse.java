package com.gdgoc.study_group.comment.dto;

import com.gdgoc.study_group.comment.domain.Comment;
import io.swagger.v3.oas.annotations.media.Schema;

public record CommentResponse(
        @Schema(description = "댓글을 작성한 멤버 ID") Long memberId,
        @Schema(description = "댓글 내용") String comment) {

    public static CommentResponse from(Comment comment) {
        return new CommentResponse(
                comment.getMember().getId(),
                comment.getComment()
        );
    }
}
