package com.gdgoc.study_group.round.controller;

import com.gdgoc.study_group.round.dto.CreateRoundRequest;
import com.gdgoc.study_group.round.dto.RoundDTO;
import com.gdgoc.study_group.round.dto.RoundThumbnailDTO;
import com.gdgoc.study_group.round.dto.UpdateRoundRequest;
import com.gdgoc.study_group.round.service.RoundService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/studies/{studyId}/rounds")
@RequiredArgsConstructor
@Tag(name = "Round API", description = "스터디 회차 관련 API")
public class RoundController {

  private final RoundService roundService;

  @GetMapping
  @Operation(summary = "특정 스터디의 모든 회차 조회", description = "특정 스터디의 모든 회차 정보를 조회합니다.")
  public ResponseEntity<List<RoundDTO>> getRoundsByStudy(
          @Parameter(description = "스터디 ID", required = true) @PathVariable Long studyId) {
    return ResponseEntity.ok(roundService.getRoundsByStudyId(studyId));
  }

  @GetMapping("/{roundId}")
  @Operation(summary = "특정 회차 정보 조회", description = "특정 회차의 정보를 조회합니다.")
  public ResponseEntity<RoundDTO> getRound(
          @Parameter(description = "스터디 ID", required = true) @PathVariable Long studyId,
          @Parameter(description = "회차 ID", required = true) @PathVariable Long roundId) {
    return ResponseEntity.ok(roundService.getRound(roundId));
  }

  @PostMapping
  @Operation(summary = "회차 생성", description = "새로운 회차를 생성합니다.")
  public ResponseEntity<RoundDTO> createRound(
          @Parameter(description = "스터디 ID", required = true) @PathVariable Long studyId,
          @RequestBody CreateRoundRequest request) {
    return ResponseEntity.ok(roundService.createRound(studyId, request));
  }

  @PatchMapping("/{roundId}")
  @Operation(summary = "회차 수정", description = "기존 회차의 정보를 수정합니다.")
  public ResponseEntity<RoundDTO> updateRound(
          @Parameter(description = "스터디 ID", required = true) @PathVariable Long studyId,
          @Parameter(description = "회차 ID", required = true) @PathVariable Long roundId,
          @RequestBody UpdateRoundRequest request) {
    return ResponseEntity.ok(roundService.updateRound(roundId, request));
  }

  @DeleteMapping("/{roundId}")
  @Operation(summary = "회차 삭제", description = "기존 회차를 삭제합니다.")
  public ResponseEntity<Void> deleteRound(
          @Parameter(description = "스터디 ID", required = true) @PathVariable Long studyId,
          @Parameter(description = "회차 ID", required = true) @PathVariable Long roundId) {
    roundService.deleteRound(roundId);
    return ResponseEntity.noContent().build();
  }
}