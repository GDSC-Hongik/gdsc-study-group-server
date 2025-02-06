package com.gdgoc.study_group.study.api;

import com.gdgoc.study_group.exception.CustomException;
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
    try {
      StudyResponse studyDetail = studentStudyService.getStudyDetail(studyId);
      return ResponseEntity.status(HttpStatus.OK).body(studyDetail);
    } catch (CustomException e) { // study 가 없음
      return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
  }

  @Operation(summary = "스터디 수정", description = "스터디 정보를 수정합니다. 스터디장만 수정할 수 있습니다.")
  @PatchMapping("/{studyId}")
  public ResponseEntity<Long> updateStudy(
      @PathVariable("studyId") Long studyId, @RequestBody StudyCreateUpdateRequest updateRequest) {

    try {
      Long id = leaderStudyService.updateStudy(studyId, updateRequest);
      return ResponseEntity.status(HttpStatus.OK).body(id);
    } catch (CustomException e) { // 스터디가 없을 경우
      return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
  }

  @Operation(summary = "스터디 삭제", description = "스터디를 삭제합니다. 스터디장만 삭제할 수 있습니다.")
  @DeleteMapping("/{studyId}")
  public ResponseEntity<String> deleteStudy(@PathVariable("studyId") Long studyId) {

    leaderStudyService.deleteStudy(studyId);

    return ResponseEntity.status(HttpStatus.RESET_CONTENT).body("스터디가 삭제되었습니다.");
  }
}
