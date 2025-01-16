package com.gdgoc.study_group.study.api;

import com.gdgoc.study_group.study.application.StudyService;
import com.gdgoc.study_group.study.dto.StudyCreateRequest;
import com.gdgoc.study_group.study.dto.StudyCreateResponse;
import com.gdgoc.study_group.study.dto.StudyDetailResponse;
import com.gdgoc.study_group.study.dto.StudyListResponse;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/studies")
public class StudyController {

  public final StudyService studyService;

  public StudyController(StudyService studyService) {
    this.studyService = studyService;
  }

  @PostMapping()
  public ResponseEntity<StudyCreateResponse> createStudy(@RequestBody StudyCreateRequest request) {
    StudyCreateResponse newStudy = studyService.createStudy(request);

    return ResponseEntity.ok(newStudy);
  }

  @GetMapping()
  public ResponseEntity<List<StudyListResponse>> getStudyList() {
    List<StudyListResponse> studyList = studyService.getStudyList();

    return ResponseEntity.ok(studyList);
  }

  @GetMapping("/{studyId}")
  public ResponseEntity<StudyDetailResponse> getStudyDetail(@PathVariable("studyId") Long studyId) {
    StudyDetailResponse studyDetail = studyService.getStudyDetail(studyId);

    if (studyDetail == null) {
      return ResponseEntity.notFound().build();
    }

    return ResponseEntity.ok(studyDetail);
  }
}
