package com.gdgoc.study_group.comment.dto;

import com.gdgoc.study_group.comment.domain.Comment;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

public record CommentDto(
        @NotNull(message = "멤버 ID를 입력해 주세요.") @Schema(description = "댓글을 작성한 멤버의 ID") Long memberId,
        @Schema(description = "댓글 내용") String comment) {

    public static CommentDto from(Comment comment) {
        return new CommentDto(
                comment.getMember().getId(),
                comment.getComment());
    }
}
