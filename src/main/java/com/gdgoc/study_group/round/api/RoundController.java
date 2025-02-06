package com.gdgoc.study_group.round.api;

import com.gdgoc.study_group.round.application.RetrospectService;
import com.gdgoc.study_group.round.dto.CreateRoundRequest;
import com.gdgoc.study_group.round.dto.RoundDTO;
import com.gdgoc.study_group.round.dto.UpdateRoundRequest;
import com.gdgoc.study_group.round.application.RoundService;
import com.gdgoc.study_group.roundMember.dto.RetrospectDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/studies/{studyId}/rounds")
@RequiredArgsConstructor
@Tag(name = "Round API", description = "스터디 회차 관련 API")
public class RoundController {

  private final RoundService roundService;
  private final RetrospectService retrospectService;

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


  // ================ Retrospect ================ //
  @Operation(summary = "회고 작성", description = "스터디를 참여한 멤버들이 회고를 작성합니다.")
  @PostMapping("/{roundId}/retrospects")
  public ResponseEntity<Void> createRetrospect(
          @Parameter(description = "회차 ID", required = true) @PathVariable Long roundId,
          @RequestBody RetrospectDTO request) {

    retrospectService.createRetrospect(roundId, request);

    return ResponseEntity.noContent().build();
  }

  @Operation(summary = "회고 수정", description = "작성한 회고를 수정합니다.")
  @PatchMapping("/{roundId}/retrospects/{roundMemberId}")
  public ResponseEntity<Void> updateRetrospect(
          @Parameter(description = "회차 ID" ,required = true) @PathVariable Long roundId,
          @Parameter(description = "회고(round member) ID", required = true) @PathVariable Long roundMemberId,
          @RequestBody RetrospectDTO request) {

    retrospectService.updateRetrospect(roundId, roundMemberId, request);

    return ResponseEntity.noContent().build();
  }

  @Operation(summary = "회고 삭제", description = "회고를 삭제합니다.")
  @DeleteMapping("/{roundId}/retrospects/{roundMemberId}")
  public ResponseEntity<Void> deleteRetrospect(
          @Parameter(description = "회차 ID" ,required = true) @PathVariable Long roundId,
          @Parameter(description = "회고(round member) ID", required = true) @PathVariable Long roundMemberId) {

    retrospectService.deleteRetrospect(roundId, roundMemberId);

    return ResponseEntity.noContent().build();
  }

  @Operation(summary = "특정 회고 조회", description = "특정 회고를 조회합니다.")
  @GetMapping("/{roundId}/retrospects/{roundMemberId}")
  public ResponseEntity<RetrospectDTO> getRetrospect(
          @Parameter(description = "회차 ID" ,required = true) @PathVariable Long roundId,
          @Parameter(description = "회고(round member) ID", required = true) @PathVariable Long roundMemberId) {

    return ResponseEntity.ok(retrospectService.getRetrospect(roundId, roundMemberId));
  }

  @Operation(summary = "모든 회고 조회", description = "특정 라운드의 모든 회고를 조회합니다.")
  @GetMapping("/{roundId}/retrospects")
  public ResponseEntity<List<RetrospectDTO>> getAllRetrospects(
          @Parameter(description = "회차 ID" ,required = true) @PathVariable Long roundId) {

    return ResponseEntity.ok(retrospectService.getAllRetrospects(roundId));
  }
}