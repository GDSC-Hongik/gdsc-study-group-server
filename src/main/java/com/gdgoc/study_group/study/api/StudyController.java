package com.gdgoc.study_group.study.api;

import com.gdgoc.study_group.study.application.StudyService;
import com.gdgoc.study_group.study.dao.StudyRepository;
import com.gdgoc.study_group.study.dto.*;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/studies")
public class StudyController {

  public final StudyService studyService;

  public StudyController(StudyService studyService, StudyRepository studyRepository) {
    this.studyService = studyService;
  }

  @PostMapping()
  public ResponseEntity<StudyCreateResponse> createStudy(@RequestBody StudyCreateRequest request) {
    Long createdStudyId = studyService.createStudy(1L, request); // 임시 유저

    return ResponseEntity.status(HttpStatus.CREATED)
            .body(StudyCreateResponse.builder().id(createdStudyId).build());
  }

  @GetMapping()
  public ResponseEntity<List<StudyListResponse>> getStudyList() {
    List<StudyListResponse> studyList = studyService.getStudyList();

    return ResponseEntity.status(HttpStatus.OK).body(studyList);
  }

  @GetMapping("/{studyId}")
  public ResponseEntity<?> getStudyDetail(@PathVariable("studyId") Long studyId) {
    StudyDetailResponse studyDetail = studyService.getStudyDetail(studyId);

    if (studyDetail == null) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body("해당하는 스터디가 없습니다.");
    }

    return ResponseEntity.status(HttpStatus.OK).body(studyDetail);
  }

  @PatchMapping("/{studyId}")
  public ResponseEntity<?> updateStudy(
      @PathVariable("studyId") Long studyId, @RequestBody StudyCreateRequest updateRequest) {

    Long updatedStudyId = studyService.updateStudy(studyId, updateRequest);

    if (updatedStudyId == null) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    return ResponseEntity.status(HttpStatus.OK)
        .body(StudyCreateResponse.builder().id(updatedStudyId).build());
  }

  @DeleteMapping("/{studyId}")
  public ResponseEntity<String> deleteStudy(@PathVariable("studyId") Long studyId) {

    boolean isStudyExist = studyService.deleteStudy(studyId);

    if (isStudyExist) {
      return ResponseEntity.status(HttpStatus.NO_CONTENT).body("스터디가 삭제되었습니다.");
    }

    return ResponseEntity.status(HttpStatus.NOT_FOUND).body("해당하는 스터디가 없습니다.");
  }
}