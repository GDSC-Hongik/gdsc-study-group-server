package com.gdgoc.study_group.round.api;

import com.gdgoc.study_group.comment.dto.CommentDto;
import com.gdgoc.study_group.round.application.CommentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/studies/{studyId}/rounds/{roundId}/comments")
@RequiredArgsConstructor
@Tag(name = "Comment API", description = "댓글 관련 API")
public class CommentController {

    private final CommentService commentService;

    @Operation(summary = "댓글 작성", description = "댓글을 작성합니다.")
    @PostMapping
    public ResponseEntity<Map<String, Long>> createComment(
            @Parameter(description = "회차 ID", required = true) @PathVariable Long roundId,
            @RequestBody CommentDto request) {

        return ResponseEntity.ok(Map.of("comment_id", commentService.createComment(roundId, request)));
    }

    @Operation(summary = "댓글 수정", description = "댓글을 수정합니다.")
    @PatchMapping("/{commentId}")
    public ResponseEntity<Map<String, Long>> updateComment(
            @Parameter(description = "회차 ID", required = true) @PathVariable Long roundId,
            @Parameter(description = "수정할 댓글 ID", required = true) @PathVariable Long commentId,
            @RequestBody String updatedComment) {

        commentService.updateComment(roundId, commentId, updatedComment);

        return ResponseEntity.ok(Map.of("comment_id", commentId));
    }

    @Operation(summary = "댓글 삭제", description = "댓글을 삭제합니다.")
    @DeleteMapping("/{commentId}")
    public ResponseEntity<Void> deleteComment(
            @Parameter(description = "회차 ID", required = true) @PathVariable Long roundId,
            @Parameter(description = "삭제할 댓글 ID", required = true) @PathVariable Long commentId) {

        commentService.deleteComment(roundId, commentId);

        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "특정 댓글 조회", description = "특정 댓글을 조회합니다.")
    @GetMapping("/{commentId}")
    public ResponseEntity<CommentDto> getComment(
            @Parameter(description = "회차 ID", required = true) @PathVariable Long roundId,
            @Parameter(description = "조회할 댓글 ID", required = true) @PathVariable Long commentId) {

        return ResponseEntity.ok(commentService.getComment(roundId, commentId));
    }

    @Operation(summary = "모든 댓글 조회", description = "회차의 모든 댓글을 조회합니다.")
    @GetMapping
    public ResponseEntity<List<CommentDto>> getAllComments(
            @Parameter(description = "회차 ID", required = true) @PathVariable Long roundId) {

        return ResponseEntity.ok(commentService.getAllComments(roundId));
    }
}
