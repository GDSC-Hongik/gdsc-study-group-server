package com.gdgoc.study_group.study.controller;

import com.gdgoc.study_group.study.domain.Study;
import com.gdgoc.study_group.study.dto.StudyDTO;
import com.gdgoc.study_group.study.service.StudyService;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/studies")
public class StudyController {

  @Autowired private StudyService studyService;

  @PostMapping
  public ResponseEntity<Map<String, Object>> createStudy(@RequestBody StudyDTO studyDTO) {

    Study newStudy = studyService.createStudy(studyDTO);

    Map<String, Object> response = new LinkedHashMap<>();
    response.put("message", "스터디 생성 성공");
    response.put("study_id", newStudy.getId());

    return ResponseEntity.status(HttpStatus.CREATED).body(response);
  }

  @GetMapping
  public ResponseEntity<List<Study>> getStudy() {

    List<Study> studies = studyService.getAllStudies();

    return ResponseEntity.status(HttpStatus.OK).body(studies);
  }
}
