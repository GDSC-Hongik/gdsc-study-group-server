package com.gdgoc.study_group.study.api;

import com.gdgoc.study_group.study.application.LeaderStudyService;
import com.gdgoc.study_group.study.application.StudentStudyService;
import com.gdgoc.study_group.study.dto.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/studies")
@Tag(name = "Study", description = "스터디 API")
@RequiredArgsConstructor
public class StudyController {

  public final StudentStudyService studentStudyService;
  public final LeaderStudyService leaderStudyService;

  @Operation(summary = "스터디 생성", description = "자율스터디를 생성합니다.")
  @PostMapping()
  public ResponseEntity<Long> createStudy(@RequestBody StudyCreateUpdateRequest request) {
    Long studyId = studentStudyService.createStudy(request);

    return ResponseEntity.ok(studyId);
  }

  @Operation(summary = "전체 스터디 조회", description = "모든 스터디를 조회합니다.")
  @GetMapping()
  public ResponseEntity<List<StudySimpleResponse>> getStudyList() {
    List<StudySimpleResponse> studyList = studentStudyService.getAllSimpleStudies();

    return ResponseEntity.status(HttpStatus.OK).body(studyList);
  }

  @Operation(summary = "개별 스터디 조회", description = "스터디 하나의 정보를 조회합니다.")
  @GetMapping("/{studyId}")
  public ResponseEntity<StudyResponse> getStudyDetail(@PathVariable("studyId") Long studyId) {
    StudyResponse studyDetail = studentStudyService.getStudyDetail(studyId);
    return ResponseEntity.status(HttpStatus.OK).body(studyDetail);
  }

  @Operation(summary = "스터디 수정", description = "스터디 정보를 수정합니다. 스터디장만 수정할 수 있습니다.")
  @PatchMapping("/{studyId}")
  public ResponseEntity<Long> updateStudy(
          @PathVariable("studyId") Long studyId, @RequestBody StudyCreateUpdateRequest updateRequest) {

    Long id = leaderStudyService.updateStudy(studyId, updateRequest);
    return ResponseEntity.status(HttpStatus.OK).body(id);
  }

  @Operation(summary = "스터디 삭제", description = "스터디를 삭제합니다. 스터디장만 삭제할 수 있습니다.")
  @DeleteMapping("/{studyId}")
  public ResponseEntity<Void> deleteStudy(@PathVariable("studyId") Long studyId) {
    leaderStudyService.deleteStudy(studyId);
    return ResponseEntity.status(HttpStatus.RESET_CONTENT).build();
  }

  //================== 스터디 지원 관련 ==================//
  @Operation(summary = "스터디 지원", description = "스터디에 지원합니다. 스터디 질문이 존재해야만 합니다.")
  @PostMapping("/{studyId}/applications")
  public ResponseEntity<Void> applyStudy(@PathVariable("studyId") Long studyId, @RequestBody ApplicationRequest request) {
    // TODO: auth 에서 member id 구하기
    Long memberId = 777L;

    studentStudyService.StudyApply(studyId, memberId, request.answer());
    return ResponseEntity.status(HttpStatus.OK).build();
  }

  @Operation(summary = "스터디 지원 취소", description = "스터디 지원을 취소합니다")
  @DeleteMapping("/{studyId}/applications")
  public ResponseEntity<Void> applyCancel(@PathVariable("studyId") Long studyId) {
    // TODO: auth 에서 member id 구하기
    Long memberId = 777L;

    studentStudyService.cancelApply(studyId, memberId);
    return ResponseEntity.status(HttpStatus.RESET_CONTENT).build();
  }

  @Operation(summary = "스터디 참여자 확인", description = "스터디 참여자를 확인합니다.")
  @GetMapping("/{studyId}/participants")
  public ResponseEntity<List<StudyParticipantResponse>> getStudyParticipants(@PathVariable("studyId") Long studyId) {
    return ResponseEntity.status(HttpStatus.OK).body(studentStudyService.findParticipants(studyId));
  }

  //================== leader Only ==================//
  // TODO: auth 를 통해 leader 임을 확인해야함

  @Operation(summary = "스터디 지원자 확인", description = "스터디 지원자를 확인합니다. 리더 이상의 자격이 필요합니다")
  @GetMapping("/{studyId}/applications")
  public ResponseEntity<List<StudyParticipantResponse>> studyParticipant(@PathVariable("studyId") Long studyId) {
    return ResponseEntity.status(HttpStatus.OK).body(leaderStudyService.findAppliedMember(studyId));
  }

  @DeleteMapping("/{studyId}/participants/{memberId}")
  public ResponseEntity<Void> deleteParticipants(@PathVariable("studyId") Long studyId,
                                                 @PathVariable("memberId") Long memberId) {
    leaderStudyService.participantWithdraw(studyId, memberId);
    return ResponseEntity.status(HttpStatus.OK).build();
  }

  @PatchMapping("/{studyId}/applications")
  public ResponseEntity<Void> participantChangeStatus(@PathVariable("studyId") Long studyId,
                                                      @RequestBody ParticipantStatusRequest request) {
    leaderStudyService.changeMemberStatus(studyId, request);
    return ResponseEntity.status(HttpStatus.OK).build();
  }
}
