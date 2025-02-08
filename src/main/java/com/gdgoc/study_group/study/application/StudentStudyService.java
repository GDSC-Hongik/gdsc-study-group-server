package com.gdgoc.study_group.study.application;

import static com.gdgoc.study_group.exception.ErrorCode.STUDY_NOT_FOUND;

import com.gdgoc.study_group.exception.CustomException;
import com.gdgoc.study_group.study.dao.StudyRepository;
import com.gdgoc.study_group.study.domain.Study;
import com.gdgoc.study_group.study.dto.*;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class StudentStudyService {

  public final StudyRepository studyRepository;

  /**
   * 스터디를 생성합니다.
   *
   * @param request 스터디 생성 DTO
   * @return ResponseDTO 반환
   */
  @Transactional(readOnly = false)
  public Long createStudy(StudyCreateUpdateRequest request) {

    Study study =
        Study.create(
            request.name(),
            request.description(),
            request.requirement(),
            request.question(),
            request.maxParticipants(),
            request.studyStatus());

    // TODO: 스터디를 생성한 유저를 스터디장으로 설정한 뒤 studyMembers에 추가

    study.addInfo(request.curriculums(), request.days());
    studyRepository.save(study);

    return study.getId();
  }

  /**
   * 스터디 전체 목록을 조회합니다.
   *
   * @return 스터디 전체 목록 리스트
   */
  public List<StudyResponse> getAllStudies() {
    return studyRepository.findAll().stream().map(StudyResponse::from).toList();
  }

  /**
   * 스터디 상세 정보를 조회합니다.
   *
   * @param studyId 조회할 스터디 아이디
   * @return 스터디 정보 반환
   */
  public StudyResponse getStudyDetail(Long studyId) {
    Study study =
        studyRepository.findById(studyId).orElseThrow(() -> new CustomException(STUDY_NOT_FOUND));
    return StudyResponse.from(study);
  }
}
