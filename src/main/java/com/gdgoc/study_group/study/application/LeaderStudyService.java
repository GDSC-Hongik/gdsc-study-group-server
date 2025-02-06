package com.gdgoc.study_group.study.application;

import static com.gdgoc.study_group.exception.ErrorCode.STUDY_NOT_FOUND;

import com.gdgoc.study_group.exception.CustomException;
import com.gdgoc.study_group.study.dao.StudyRepository;
import com.gdgoc.study_group.study.domain.Study;
import com.gdgoc.study_group.study.dto.StudyCreateUpdateRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class LeaderStudyService {

  // TODO: 스터디장 권한 확인 필요

  public final StudyRepository studyRepository;

  /**
   * 스터디 정보를 수정합니다.
   * @throws CustomException 스터디가 존재하지 않을 경우
   * @param studyId 수정할 스터디 ID
   * @param request 수정할 스터디의 정보
   * @return 업데이트된 스터디의 ID
   */
  @Transactional(readOnly = false)
  public Long updateStudy(Long studyId, StudyCreateUpdateRequest request) throws CustomException {
    Study study =
        studyRepository.findById(studyId).orElseThrow(() -> new CustomException(STUDY_NOT_FOUND));

    study.update(
        request.name(),
        request.description(),
        request.requirement(),
        request.question(),
        request.maxParticipants(),
        request.studyStatus(),
        request.curriculums(),
        request.days());

    return studyRepository.save(study).getId();
  }

  /**
   * 스터디장 권한 확인 필요 스터디를 삭제합니다
   *
   * @param studyId 삭제할 스터디의 아이디
   * @return 해당하는 스터디의 존재 여부
   */
  @Transactional(readOnly = false)
  public void deleteStudy(Long studyId) {
    Study study =
        studyRepository.findById(studyId).orElseThrow(() -> new CustomException(STUDY_NOT_FOUND));
    studyRepository.delete(study);
  }
}
