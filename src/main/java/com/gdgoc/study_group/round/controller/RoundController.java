package com.gdgoc.study_group.round.controller;

import com.gdgoc.study_group.round.dto.CreateRoundRequest;
import com.gdgoc.study_group.round.dto.RoundDTO;
import com.gdgoc.study_group.round.dto.UpdateRoundRequest;
import com.gdgoc.study_group.round.service.RoundService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

// TODO: CustomUserDetails 클래스를 사용하도록 수정.
@RestController
@RequestMapping("/studies/{studyId}/rounds")
@RequiredArgsConstructor
@Validated
@Slf4j
@Tag(name = "Round API", description = """
    스터디 회차 관리 API
    
    스터디의 각 회차를 생성, 조회, 수정, 삭제하는 기능을 제공합니다.
    
    **주요 기능**
    - 스터디 회차 목록 조회
    - 특정 회차 상세 정보 조회
    - 새로운 회차 생성 (스터디 리더 전용)
    - 회차 정보 수정 (스터디 리더 전용)
    - 회차 삭제 (스터디 리더 전용)
    
    **권한 체계**
    - 조회: 모든 사용자 가능
    - 생성/수정/삭제: 스터디 리더만 가능
    """)
public class RoundController {
    private final RoundService roundService;

    @GetMapping
    @Operation(
            summary = "스터디 전체 회차 목록 조회",
            description = """
            특정 스터디의 모든 회차 정보를 조회합니다.
            회차는 진행 날짜(roundDate) 기준으로 오름차순 정렬됩니다.
            """
    )
    @io.swagger.v3.oas.annotations.responses.ApiResponse(
            responseCode = "200",
            description = "회차 목록 조회 성공"
    )
    @io.swagger.v3.oas.annotations.responses.ApiResponse(
            responseCode = "404",
            description = "스터디를 찾을 수 없음",
            content = @Content(
                    mediaType = "application/json",
                    examples = @ExampleObject(
                            value = """
                    {
                      "errorCodeName": "STUDY_NOT_FOUND",
                      "errorMessage": "존재하지 않는 스터디입니다."
                    }
                    """
                    )
            )
    )
    public List<RoundDTO> getRoundsByStudy(
            @Parameter(description = "스터디 ID", required = true, example = "1")
            @PathVariable Long studyId
    ) {
        return roundService.getRoundsByStudyId(studyId);
    }

    @GetMapping("/{roundId}")
    @Operation(
            summary = "특정 회차 상세 정보 조회",
            description = "특정 회차의 상세 정보를 조회합니다."
    )
    @io.swagger.v3.oas.annotations.responses.ApiResponse(
            responseCode = "200",
            description = "회차 상세 정보 조회 성공"
    )
    @io.swagger.v3.oas.annotations.responses.ApiResponse(
            responseCode = "404",
            description = "회차를 찾을 수 없음",
            content = @Content(
                    mediaType = "application/json",
                    examples = @ExampleObject(
                            value = """
                    {
                      "errorCodeName": "ROUND_NOT_FOUND",
                      "errorMessage": "존재하지 않는 회차입니다."
                    }
                    """
                    )
            )
    )
    public RoundDTO getRound(
            @Parameter(description = "스터디 ID", required = true, example = "1")
            @PathVariable Long studyId,
            @Parameter(description = "회차 ID", required = true, example = "1")
            @PathVariable Long roundId
    ) {
        return roundService.getRound(roundId);
    }

    @PostMapping
    @Operation(
            summary = "새로운 회차 생성",
            description = """
            스터디에 새로운 회차를 생성합니다.
            스터디 리더만이 회차를 생성할 수 있습니다.
            """
    )
    @io.swagger.v3.oas.annotations.responses.ApiResponse(
            responseCode = "200",
            description = "회차 생성 성공"
    )
    @io.swagger.v3.oas.annotations.responses.ApiResponse(
            responseCode = "403",
            description = "권한 없음",
            content = @Content(
                    mediaType = "application/json",
                    examples = @ExampleObject(
                            value = """
                    {
                      "errorCodeName": "NOT_STUDY_LEADER",
                      "errorMessage": "스터디 리더만 이 작업을 수행할 수 있습니다."
                    }
                    """
                    )
            )
    )
    public RoundDTO createRound(
            @Parameter(description = "스터디 ID", required = true, example = "1")
            @PathVariable Long studyId,
            @Parameter(description = "현재 인증된 사용자 정보", hidden = true)
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @Parameter(description = "회차 생성 요청 정보", required = true)
            @RequestBody CreateRoundRequest request
    ) {
        return roundService.createRound(studyId, userDetails.getUsername(), request);
    }

    @PatchMapping("/{roundId}")
    @Operation(
            summary = "회차 정보 수정",
            description = """
            기존 회차의 정보를 수정합니다.
            스터디 리더만이 회차 정보를 수정할 수 있습니다.
            """
    )
    @io.swagger.v3.oas.annotations.responses.ApiResponse(
            responseCode = "200",
            description = "회차 수정 성공"
    )
    @io.swagger.v3.oas.annotations.responses.ApiResponse(
            responseCode = "403",
            description = "권한 없음",
            content = @Content(
                    mediaType = "application/json",
                    examples = @ExampleObject(
                            value = """
                    {
                      "errorCodeName": "NOT_STUDY_LEADER",
                      "errorMessage": "스터디 리더만 이 작업을 수행할 수 있습니다."
                    }
                    """
                    )
            )
    )
    @io.swagger.v3.oas.annotations.responses.ApiResponse(
            responseCode = "404",
            description = "회차를 찾을 수 없음",
            content = @Content(
                    mediaType = "application/json",
                    examples = @ExampleObject(
                            value = """
                    {
                      "errorCodeName": "ROUND_NOT_FOUND",
                      "errorMessage": "존재하지 않는 회차입니다."
                    }
                    """
                    )
            )
    )
    public RoundDTO updateRound(
            @Parameter(description = "스터디 ID", required = true, example = "1")
            @PathVariable Long studyId,
            @Parameter(description = "회차 ID", required = true, example = "1")
            @PathVariable Long roundId,
            @Parameter(description = "현재 인증된 사용자 정보", hidden = true)
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @Parameter(description = "회차 수정 요청 정보", required = true)
            @RequestBody UpdateRoundRequest request
    ) {
        return roundService.updateRound(studyId, userDetails.getUsername(), roundId, request);
    }

    @DeleteMapping("/{roundId}")
    @Operation(
            summary = "회차 삭제",
            description = """
            특정 회차를 삭제합니다.
            스터디 리더만이 회차를 삭제할 수 있습니다.
            삭제된 회차는 복구할 수 없습니다.
            """
    )
    @io.swagger.v3.oas.annotations.responses.ApiResponse(
            responseCode = "200",
            description = "회차 삭제 성공"
    )
    @io.swagger.v3.oas.annotations.responses.ApiResponse(
            responseCode = "403",
            description = "권한 없음",
            content = @Content(
                    mediaType = "application/json",
                    examples = @ExampleObject(
                            value = """
                    {
                      "errorCodeName": "NOT_STUDY_LEADER",
                      "errorMessage": "스터디 리더만 이 작업을 수행할 수 있습니다."
                    }
                    """
                    )
            )
    )
    @io.swagger.v3.oas.annotations.responses.ApiResponse(
            responseCode = "404",
            description = "회차를 찾을 수 없음",
            content = @Content(
                    mediaType = "application/json",
                    examples = @ExampleObject(
                            value = """
                    {
                      "errorCodeName": "ROUND_NOT_FOUND",
                      "errorMessage": "존재하지 않는 회차입니다."
                    }
                    """
                    )
            )
    )
    public void deleteRound(
            @Parameter(description = "스터디 ID", required = true, example = "1")
            @PathVariable Long studyId,
            @Parameter(description = "회차 ID", required = true, example = "1")
            @PathVariable Long roundId,
            @Parameter(description = "현재 인증된 사용자 정보", hidden = true)
            @AuthenticationPrincipal CustomUserDetails userDetails
    ) {
        roundService.deleteRound(studyId, userDetails.getUsername(), roundId);
    }
}