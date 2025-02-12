package com.gdgoc.study_group.study.application;

import static com.gdgoc.study_group.exception.ErrorCode.APPLY_NO_MEMBER;
import static com.gdgoc.study_group.exception.ErrorCode.APPLY_TOO_MANY;
import static com.gdgoc.study_group.exception.ErrorCode.LEADER_NO_RIGHT;
import static com.gdgoc.study_group.exception.ErrorCode.STUDY_NOT_FOUND;

import com.gdgoc.study_group.exception.CustomException;
import com.gdgoc.study_group.study.dao.StudyRepository;
import com.gdgoc.study_group.study.domain.Study;
import com.gdgoc.study_group.study.dto.StudyCreateUpdateRequest;
import com.gdgoc.study_group.study.dto.StudyParticipantResponse;
import com.gdgoc.study_group.studyMember.domain.StudyMember;
import com.gdgoc.study_group.studyMember.domain.StudyMemberStatus;
import java.util.List;
import java.util.stream.Collectors;
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
   * @throws CustomException 스터디가 존재하지 않을 경우
   * @param studyId 삭제할 스터디의 아이디
   * @return 해당하는 스터디의 존재 여부
   */
  @Transactional(readOnly = false)
  public void deleteStudy(Long studyId) throws CustomException {
    Study study =
        studyRepository.findById(studyId).orElseThrow(() -> new CustomException(STUDY_NOT_FOUND));
    studyRepository.delete(study);
  }

  /**
   * 스터디 지원자 전체를 조회합니다
   * @param studyId 조회할 스터디 id
   * @return 지원자 전체의 정보를 반환합니다
   * @see StudyParticipantResponse
   * @throws CustomException {@code STUDY_NOT_FOUND}: 해당하는 스터디가 없습니다
   */
  public List<StudyParticipantResponse> findAppliedMember(Long studyId) throws CustomException {
    studyRepository.findById(studyId).orElseThrow(() -> new CustomException(STUDY_NOT_FOUND));

    return studyRepository.findStudyMembersWithStatus(studyId, StudyMemberStatus.WAITING).stream()
            .map(StudyParticipantResponse::from)
            .toList();
  }

  /**
   * 참가자를 탈퇴처리합니다
   * 내부적으로는 CANCEL 상태로 변경 후 저장합니다
   * @param studyId 해당 스터디의 id
   * @param participantId 해당 참가자의 id
   * @throws CustomException <br>
   * 해당하는 정보가 없다면, {@code APPLY_NO_MEMBER} 를, <br>
   * 1개를 초과한다면, {@code APPLY_TOO_MANY} 를 반환합니다 <br>
   * 참가자가 아닌 멤버를 취소 처리하려고 하면, {@code LEADER_NO_RIGHT} 를 반환합니다
   */
  @Transactional(readOnly = false)
  public void participantWithdraw(Long studyId, Long participantId) throws CustomException {
    List<StudyMember> memberInfo = studyRepository.findMemberInfo(studyId, participantId);
    if(memberInfo.isEmpty()) {
      throw new CustomException(APPLY_NO_MEMBER);
    }
    if(memberInfo.size() > 1) {
      throw new CustomException(APPLY_TOO_MANY);
    }
    StudyMember member = memberInfo.get(0);
    if(member.getStudyMemberStatus() != StudyMemberStatus.PARTICIPANT) {
      throw new CustomException(LEADER_NO_RIGHT);
    }

    member.cancel();
  }
}
