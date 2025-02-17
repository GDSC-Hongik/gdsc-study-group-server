package com.gdgoc.study_group.round.controller;

import com.gdgoc.study_group.round.dto.CreateRoundRequest;
import com.gdgoc.study_group.round.dto.RoundResponse;
import com.gdgoc.study_group.round.dto.UpdateRoundRequest;
import com.gdgoc.study_group.round.service.RoundService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Round API", description = "스터디 회차 관리 API")
@RestController
@RequestMapping("/studies/{studyId}/rounds")
@RequiredArgsConstructor
@Validated
@Slf4j
public class RoundController {
    private final RoundService roundService;

    @Operation(summary = "스터디 전체 회차 목록 조회")
    @GetMapping
    public ResponseEntity<List<RoundResponse>> getRoundsByStudy(
            @Parameter(description = "스터디 ID", required = true, example = "1")
            @PathVariable Long studyId
    ) {
        List<RoundResponse> rounds = roundService.getRoundsByStudyId(studyId);
        return ResponseEntity.ok(rounds);
    }

    @Operation(summary = "특정 회차 상세 정보 조회")
    @GetMapping("/{roundId}")
    public ResponseEntity<RoundResponse> getRound(
            @Parameter(description = "스터디 ID", required = true, example = "1")
            @PathVariable Long studyId,
            @Parameter(description = "회차 ID", required = true, example = "1")
            @PathVariable Long roundId
    ) {
        RoundResponse round = roundService.getRound(roundId);
        return ResponseEntity.ok(round);
    }

    @Operation(summary = "새로운 회차 생성")
    @PostMapping
    public ResponseEntity<RoundResponse> createRound(
            @Parameter(description = "스터디 ID", required = true, example = "1")
            @PathVariable Long studyId,
            @Parameter(description = "현재 인증된 사용자 정보", hidden = true)
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @Parameter(description = "회차 생성 요청 정보", required = true)
            @RequestBody CreateRoundRequest request
    ) {
        RoundResponse createdRound = roundService.createRound(studyId, userDetails.getUsername(), request);
        return ResponseEntity.ok(createdRound);
    }

    @Operation(summary = "회차 정보 수정")
    @PatchMapping("/{roundId}")
    public ResponseEntity<RoundResponse> updateRound(
            @Parameter(description = "스터디 ID", required = true, example = "1")
            @PathVariable Long studyId,
            @Parameter(description = "회차 ID", required = true, example = "1")
            @PathVariable Long roundId,
            @Parameter(description = "현재 인증된 사용자 정보", hidden = true)
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @Parameter(description = "회차 수정 요청 정보", required = true)
            @RequestBody UpdateRoundRequest request
    ) {
        RoundResponse updatedRound = roundService.updateRound(studyId, userDetails.getUsername(), roundId, request);
        return ResponseEntity.ok(updatedRound);
    }

    @Operation(summary = "회차 삭제")
    @DeleteMapping("/{roundId}")
    public ResponseEntity<Void> deleteRound(
            @Parameter(description = "스터디 ID", required = true, example = "1")
            @PathVariable Long studyId,
            @Parameter(description = "회차 ID", required = true, example = "1")
            @PathVariable Long roundId,
            @Parameter(description = "현재 인증된 사용자 정보", hidden = true)
            @AuthenticationPrincipal CustomUserDetails userDetails
    ) {
        roundService.deleteRound(studyId, userDetails.getUsername(), roundId);
        return ResponseEntity.ok().build();
    }
}