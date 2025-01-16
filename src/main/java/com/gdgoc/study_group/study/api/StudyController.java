package com.gdgoc.study_group.study.api;

import com.gdgoc.study_group.study.application.StudyService;
import com.gdgoc.study_group.study.domain.Study;
import com.gdgoc.study_group.study.dto.StudyCreateRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/studies")
public class StudyController {

  public final StudyService studyService;

  public StudyController(StudyService studyService) {
    this.studyService = studyService;
  }

  @PostMapping()
  public ResponseEntity<Study> createStudy(@RequestBody StudyCreateRequest request) {
    Study newStudy = studyService.createStudy(request);

    return ResponseEntity.ok(newStudy);
  }
}
