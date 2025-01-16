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
  private final StudyRepository studyRepository;

  public StudyController(StudyService studyService, StudyRepository studyRepository) {
    this.studyService = studyService;
    this.studyRepository = studyRepository;
  }

  @PostMapping()
  public ResponseEntity<StudyCreateResponse> createStudy(@RequestBody StudyCreateRequest request) {
    StudyCreateResponse newStudy = studyService.createStudy(request);

    return ResponseEntity.status(HttpStatus.CREATED).body(newStudy);
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
      MessageResponse messageResponse = MessageResponse.builder().message("해당하는 스터디가 없습니다.").build();

      return ResponseEntity.status(HttpStatus.NOT_FOUND).body(messageResponse);
    }

    return ResponseEntity.status(HttpStatus.OK).body(studyDetail);
  }

  @DeleteMapping("/{studyId}")
  public ResponseEntity<MessageResponse> deleteStudy(@PathVariable("studyId") Long studyId) {

    MessageResponse messageResponse = studyService.deleteStudy(studyId);

    if (messageResponse == null) {
      messageResponse = MessageResponse.builder().message("스터디가 삭제되었습니다.").build();
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body(messageResponse);
    }

    return ResponseEntity.status(HttpStatus.OK).body(messageResponse);
  }
}
