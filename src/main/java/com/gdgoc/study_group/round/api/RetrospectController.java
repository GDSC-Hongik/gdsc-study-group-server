package com.gdgoc.study_group.round.api;

import com.gdgoc.study_group.round.application.RetrospectService;
import com.gdgoc.study_group.roundMember.dto.RetrospectDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/studies/{studyId}/rounds/{roundId}/retrospects")
@Tag(name = "Retrospect API", description = "회고 관련 API")
public class RetrospectController {

    private final RetrospectService retrospectService;

    @Operation(summary = "회고 작성", description = "스터디를 참여한 멤버들이 회고를 작성합니다.")
    @PostMapping
    public ResponseEntity<Map<String, Long>> createRetrospect(
            @Parameter(description = "회차 ID", required = true) @PathVariable Long roundId,
            @RequestBody RetrospectDTO request) {

        return ResponseEntity.ok(Map.of("round_member_id", retrospectService.createRetrospect(roundId, request)));
    }

    @Operation(summary = "회고 수정", description = "작성한 회고를 수정합니다.")
    @PatchMapping("/{roundMemberId}")
    public ResponseEntity<Map<String, Long>> updateRetrospect(
            @Parameter(description = "회차 ID" ,required = true) @PathVariable Long roundId,
            @Parameter(description = "회고(round member) ID", required = true) @PathVariable Long roundMemberId,
            @RequestBody RetrospectDTO request) {

        return ResponseEntity.ok(Map.of("round_member_id", retrospectService.updateRetrospect(roundId, roundMemberId, request)));
    }

    @Operation(summary = "회고 삭제", description = "회고를 삭제합니다.")
    @DeleteMapping("/{roundMemberId}")
    public ResponseEntity<Void> deleteRetrospect(
            @Parameter(description = "회차 ID" ,required = true) @PathVariable Long roundId,
            @Parameter(description = "회고(round member) ID", required = true) @PathVariable Long roundMemberId) {

        retrospectService.deleteRetrospect(roundId, roundMemberId);

        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "특정 회고 조회", description = "특정 회고를 조회합니다.")
    @GetMapping("/{roundMemberId}")
    public ResponseEntity<RetrospectDTO> getRetrospect(
            @Parameter(description = "회차 ID" ,required = true) @PathVariable Long roundId,
            @Parameter(description = "회고(round member) ID", required = true) @PathVariable Long roundMemberId) {

        return ResponseEntity.ok(retrospectService.getRetrospect(roundId, roundMemberId));
    }

    @Operation(summary = "모든 회고 조회", description = "특정 라운드의 모든 회고를 조회합니다.")
    @GetMapping
    public ResponseEntity<List<RetrospectDTO>> getAllRetrospects(
            @Parameter(description = "회차 ID" ,required = true) @PathVariable Long roundId) {

        return ResponseEntity.ok(retrospectService.getAllRetrospects(roundId));
    }
}
