package com.gdgoc.study_group.comment.api;

import com.gdgoc.study_group.comment.application.CommentService;
import com.gdgoc.study_group.comment.dto.CommentRequest;
import com.gdgoc.study_group.comment.dto.CommentResponse;
import com.gdgoc.study_group.round.api.CustomUserDetails;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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
            @Parameter(description = "현재 인증된 사용자 정보", hidden = true) @AuthenticationPrincipal CustomUserDetails userDetails,
            @RequestBody CommentRequest comment) {

        return ResponseEntity.ok(
                Map.of("comment_id", commentService.createComment(roundId, userDetails.getUsername(), comment)));
    }

    @Operation(summary = "댓글 수정", description = "댓글을 수정합니다.")
    @PatchMapping("/{commentId}")
    public ResponseEntity<Map<String, Long>> updateComment(
            @Parameter(description = "회차 ID", required = true) @PathVariable Long roundId,
            @Parameter(description = "현재 인증된 사용자 정보", hidden = true) @AuthenticationPrincipal CustomUserDetails userDetails,
            @Parameter(description = "수정할 댓글 ID", required = true) @PathVariable Long commentId,
            @RequestBody CommentRequest updatedComment) {

        commentService.updateComment(roundId, userDetails.getUsername(), commentId, updatedComment);

        return ResponseEntity.ok(Map.of("comment_id", commentId));
    }

    @Operation(summary = "댓글 삭제", description = "댓글을 삭제합니다.")
    @DeleteMapping("/{commentId}")
    public ResponseEntity<Void> deleteComment(
            @Parameter(description = "회차 ID", required = true) @PathVariable Long roundId,
            @Parameter(description = "현재 인증된 사용자 정보", hidden = true) @AuthenticationPrincipal CustomUserDetails userDetails,
            @Parameter(description = "삭제할 댓글 ID", required = true) @PathVariable Long commentId) {

        commentService.deleteComment(roundId, userDetails.getUsername(), commentId);

        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "특정 댓글 조회", description = "특정 댓글을 조회합니다.")
    @GetMapping("/{commentId}")
    public ResponseEntity<CommentResponse> getComment(
            @Parameter(description = "회차 ID", required = true) @PathVariable Long roundId,
            @Parameter(description = "조회할 댓글 ID", required = true) @PathVariable Long commentId) {

        return ResponseEntity.ok(commentService.getComment(roundId, commentId));
    }

    @Operation(summary = "모든 댓글 조회", description = "회차의 모든 댓글을 조회합니다.")
    @GetMapping
    public ResponseEntity<List<CommentResponse>> getAllComments(
            @Parameter(description = "회차 ID", required = true) @PathVariable Long roundId) {

        return ResponseEntity.ok(commentService.getAllComments(roundId));
    }
}
