package com.gdgoc.study_group.study.controller;

import com.gdgoc.study_group.study.domain.Study;
import com.gdgoc.study_group.study.dto.StudyCreateRequestDTO;
import com.gdgoc.study_group.study.dto.StudyCreateResponseDTO;
import com.gdgoc.study_group.study.dto.StudyListResponseDTO;
import com.gdgoc.study_group.study.service.StudyService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/studies")
public class StudyController {

  @Autowired private StudyService studyService;

  @PostMapping
  public ResponseEntity<StudyCreateResponseDTO> createStudy(
      @RequestBody StudyCreateRequestDTO studyCreateRequestDTO) {

    Study newStudy = studyService.createStudy(studyCreateRequestDTO);
    StudyCreateResponseDTO studyCreateResponseDTO =
        new StudyCreateResponseDTO("스터디 생성 성공", newStudy.getId());

    return ResponseEntity.status(HttpStatus.CREATED).body(studyCreateResponseDTO);
  }

  @GetMapping
  public ResponseEntity<List<StudyListResponseDTO>> getStudyList() {

    List<StudyListResponseDTO> studyList = studyService.getStudyList();

    return ResponseEntity.status(HttpStatus.OK).body(studyList);
  }
}
