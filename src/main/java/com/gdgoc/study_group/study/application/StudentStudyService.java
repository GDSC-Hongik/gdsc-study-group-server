package com.gdgoc.study_group.study.application;

import static com.gdgoc.study_group.exception.ErrorCode.APPLY_NO_MEMBER;
import static com.gdgoc.study_group.exception.ErrorCode.APPLY_NO_QUESTION;
import static com.gdgoc.study_group.exception.ErrorCode.APPLY_TOO_MANY;
import static com.gdgoc.study_group.exception.ErrorCode.MEMBER_NOT_FOUND;
import static com.gdgoc.study_group.exception.ErrorCode.STUDY_LEADER_ERROR;
import static com.gdgoc.study_group.exception.ErrorCode.STUDY_NOT_FOUND;

import com.gdgoc.study_group.exception.CustomException;
import com.gdgoc.study_group.member.dao.MemberRepository;
import com.gdgoc.study_group.member.domain.Member;
import com.gdgoc.study_group.study.dao.StudyRepository;
import com.gdgoc.study_group.study.domain.Study;
import com.gdgoc.study_group.study.dto.*;
import com.gdgoc.study_group.studyMember.domain.StudyMember;
import com.gdgoc.study_group.studyMember.domain.StudyMemberStatus;
import java.util.List;
import java.util.stream.Stream;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class StudentStudyService {

  private final StudyRepository studyRepository;
  private final MemberRepository memberRepository;

  /**
   * 스터디를 생성합니다.
   *
   * @param request 스터디 생성 DTO
   * @return ResponseDTO 반환
   */
  @Transactional(readOnly = false)
  public Long createStudy(StudyCreateUpdateRequest request) {

    Study study =
        Study.builder()
                .name(request.name())
                .description(request.description())
                .requirement(request.requirement())
                .question(request.question())
                .maxParticipants(request.maxParticipants())
                .studyStatus(request.studyStatus()).build();

    study.addInfo(request.curriculums(), request.days());

    // study leader 설정
    // TODO: 스터디를 생성한 유저를 스터디장으로 설정한 뒤 studyMembers에 추가
    StudyMember leader = StudyMember.builder()
//            .member() // TODO: AUTH 구현 후 토큰을 통해 구할 예정
            .study(study)
            .studyMemberStatus(StudyMemberStatus.LEADER)
            .build();
    study.getStudyMembers().add(leader);

    return studyRepository.save(study).getId();
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
   * 스터디 전체 목록을 간략화해서 반환합니다
   * @see  StudySimpleResponse
   * @return 간략화된 스터디 전체 목록 리스트
   */
  public List<StudySimpleResponse> getAllSimpleStudies() {
    return studyRepository.findAll().stream().map(StudySimpleResponse::from).toList();
  }

  /**
   * 스터디 상세 정보를 조회합니다.
   *
   * @param studyId 조회할 스터디 아이디
   * @throws CustomException 스터디가 존재하지 않을 경우
   * @return 스터디 정보 반환
   */
  public StudyResponse getStudyDetail(Long studyId) throws CustomException {
    Study study =
        studyRepository.findById(studyId).orElseThrow(() -> new CustomException(STUDY_NOT_FOUND));
    return StudyResponse.from(study);
  }

  /**
   * 스터디 참여자를 조회합니다
   * @param studyId
   * @return 참여자 배열을 반환합니다
   * @throws CustomException <br>
   * {@code STUDY_NOT_FOUND}: 해당하는 스터디가 없습니다
   * {@code STUDY_LEADER_ERROR}: 스터디 리더가 1명이 아닙니다
   */
  public List<StudyParticipantResponse> findParticipants(Long studyId) throws CustomException {
    studyRepository.findById(studyId).orElseThrow(() -> new CustomException(STUDY_NOT_FOUND));

    List<StudyMember> participants = studyRepository.findStudyMembersWithStatus(studyId,
            StudyMemberStatus.PARTICIPANT);
    List<StudyMember> leader = studyRepository.findStudyMembersWithStatus(studyId,
            StudyMemberStatus.LEADER);
    if(leader.size() != 1) {
      throw new CustomException(STUDY_LEADER_ERROR);
    }

    return Stream.concat(participants.stream(), leader.stream())
            .map(StudyParticipantResponse::from)
            .toList();
  }

  //============== apply ==============//
  /**
   * 스터디에 지원합니다
   * 질문이 없는 경우, 답변에 null 로 저장됩니다 <br>
   * <strong>답변의 존재 여부와 관계없이 같은 study, member id 조합은 1개여야합니다</strong>
   * @param studyId 지원할 스터디 id
   * @param memberId 지원할 멤버 id
   * @param answer 답변, nullable
   * @throws CustomException </br> {@code STUDY_NOT_FOUND}, {@code MEMBER_NOT_FOUND}, </br>
   * {@code APPLY_NO_QUESTION}: 각각 해당하는 정보가 없습니다 </br>
   * {@code ANSWER_TOO_MANY}: 지원이 이미 존재합니다 </br>
   */
  @Transactional(readOnly = false)
  public void StudyApply(Long studyId, Long memberId, String answer) throws CustomException {
    Study study = studyRepository.findById(studyId).orElseThrow(() -> new CustomException(STUDY_NOT_FOUND));
    Member member = memberRepository.findById(memberId).orElseThrow(() -> new CustomException(MEMBER_NOT_FOUND));

    // validate
    List<StudyMember> memberInfos = studyRepository.findMemberInfo(studyId, memberId);
    if(!memberInfos.isEmpty()) {
      throw new CustomException(APPLY_TOO_MANY);
    }
    if(study.getQuestion() == null && answer != null) {
      throw new CustomException(APPLY_NO_QUESTION);
    }

    // make apply info
    StudyMember studyMember = StudyMember.builder()
            .study(study)
            .member(member)
            .answer(answer)
            .studyMemberStatus(StudyMemberStatus.WAITING)
            .build();

    // save
    study.addStudyMember(studyMember);
    studyRepository.save(study);
  }

  /**
   * 지원을 취소합니다
   * @param studyId 취소할 스터디 id
   * @param memberId 취소할 멤버 id
   * @throws CustomException <br>
   * {@code STUDY_NOT_FOUND}: 해당하는 스터디 정보가 없습니다 </br>
   * {@code APPLY_NO_MEMBER}: 해당하는 지원 정보가 없습니다 </br>
   * {@code APPLY_TOO_MANY}: 해당하는 지원 정보가 너무 많습니다 </br>
   */
  @Transactional(readOnly = false)
  public void cancelApply(Long studyId, Long memberId) throws CustomException {
    Study study = studyRepository.findById(studyId).orElseThrow(() -> new CustomException(STUDY_NOT_FOUND));
    List<StudyMember> studyMembers = studyRepository.findStudyMembersWithStatus(studyId, StudyMemberStatus.WAITING);

    // validate
    if(studyMembers.isEmpty()) {
      throw new CustomException(APPLY_NO_MEMBER);
    }
    if(studyMembers.size() > 1) {
      throw new CustomException(APPLY_TOO_MANY);
    }

    studyMembers.get(0).cancel();
    studyRepository.save(study);
  }
}
