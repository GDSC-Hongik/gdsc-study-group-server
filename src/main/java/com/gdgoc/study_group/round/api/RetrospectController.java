package com.gdgoc.study_group.round.api;

import com.gdgoc.study_group.round.application.RetrospectService;
import com.gdgoc.study_group.round.dto.RetrospectRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/studies/{studyId}/rounds/{roundId}/retrospects")
@Tag(name = "Retrospect API", description = "회고 관련 API")
public class RetrospectController {

    private final RetrospectService retrospectService;

    @Operation(summary = "회고 작성", description = "스터디를 참여한 멤버들이 회고를 작성합니다.")
    @PostMapping
    public ResponseEntity<Void> createRetrospect(
            @Parameter(description = "회차 ID", required = true) @PathVariable Long roundId,
            @Parameter(description = "현재 인증된 사용자 정보", hidden = true) @AuthenticationPrincipal CustomUserDetails userDetails,
            @RequestBody RetrospectRequest request) {

        retrospectService.createRetrospect(roundId, userDetails.getUsername(), request);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "회고 수정", description = "작성한 회고를 수정합니다.")
    @PatchMapping
    public ResponseEntity<Map<String, Long>> updateRetrospect(
            @Parameter(description = "회차 ID" ,required = true) @PathVariable Long roundId,
            @Parameter(description = "현재 인증된 사용자 정보", hidden = true) @AuthenticationPrincipal CustomUserDetails userDetails,
            @RequestBody RetrospectRequest request) {

        retrospectService.updateRetrospect(roundId, userDetails.getUsername(), request);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "회고 삭제", description = "회고를 삭제합니다.")
    @DeleteMapping
    public ResponseEntity<Void> deleteRetrospect(
            @Parameter(description = "회차 ID" ,required = true) @PathVariable Long roundId,
            @Parameter(description = "현재 인증된 사용자 정보", hidden = true) @AuthenticationPrincipal CustomUserDetails userDetails) {

        retrospectService.deleteRetrospect(roundId, userDetails.getUsername());
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "회고 조회", description = "특정 라운드의 모든 회고를 조회합니다. `?filter=memberId` 파라미터를 사용하면 특정 멤버의 회고만 조회할 수 있습니다.")
    @GetMapping
    public ResponseEntity<?> getAllRetrospects(
            @Parameter(description = "회차 ID" ,required = true) @PathVariable Long roundId,
            @RequestParam(value = "filter", required = false) Long memberId) {

        if (memberId != null) { // 특정 회고 조회
            return ResponseEntity.ok(retrospectService.getRetrospect(roundId, memberId));
        }

        // 전체 회고 조회
        return ResponseEntity.ok(retrospectService.getAllRetrospects(roundId));
    }
}
