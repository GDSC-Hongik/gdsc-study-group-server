package com.gdgoc.study_group.study.application;

import static com.gdgoc.study_group.exception.ErrorCode.MEMBER_NOT_FOUND;
import static com.gdgoc.study_group.exception.ErrorCode.STUDY_NOT_FOUND;
import static com.gdgoc.study_group.exception.ErrorCode.STUDY_QUESTION_NOT_FOUND;

import com.gdgoc.study_group.answer.domain.Answer;
import com.gdgoc.study_group.exception.CustomException;
import com.gdgoc.study_group.exception.ErrorCode;
import com.gdgoc.study_group.member.dao.MemberRepository;
import com.gdgoc.study_group.member.domain.Member;
import com.gdgoc.study_group.study.dao.StudyRepository;
import com.gdgoc.study_group.study.domain.Study;
import com.gdgoc.study_group.study.dto.*;
import com.gdgoc.study_group.studyMember.domain.StudyMember;
import com.gdgoc.study_group.studyMember.domain.StudyMemberStatus;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.View;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class StudentStudyService {

  public final StudyRepository studyRepository;
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
   * 스터디에 지원합니다
   * @param studyId 지원할 스터디 id
   * @param memberId 지원할 멤버 id
   * @param answer 답변
   * @return 답변 id
   * @throws CustomException 스터디 혹은 멤버가 없을 경우 반환
   */
  @Transactional(readOnly = false)
  public Long StudyApply(Long studyId, Long memberId, String answer) throws CustomException {
    Study study = studyRepository.findById(studyId).orElseThrow(() -> new CustomException(STUDY_NOT_FOUND));
    Member member = memberRepository.findById(memberId).orElseThrow(() -> new CustomException(MEMBER_NOT_FOUND));
    String question = study.getQuestion();
    if(question == null) {
      throw new CustomException(STUDY_QUESTION_NOT_FOUND);
    }
    Answer addedAnswer = study.addAnswer(member, answer);
    studyRepository.save(study);

    return addedAnswer.getId();
  }
}
