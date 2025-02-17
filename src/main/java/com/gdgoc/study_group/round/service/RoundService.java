package com.gdgoc.study_group.round.service;

import com.gdgoc.study_group.exception.CustomException;
import com.gdgoc.study_group.exception.ErrorCode;
import com.gdgoc.study_group.round.domain.Round;
import com.gdgoc.study_group.round.dto.CreateRoundRequest;
import com.gdgoc.study_group.round.dto.UpdateRoundRequest;
import com.gdgoc.study_group.round.dto.RoundResponse;
import com.gdgoc.study_group.round.repository.RoundRepository;
import com.gdgoc.study_group.study.dao.StudyRepository;
import com.gdgoc.study_group.study.domain.Study;
import com.gdgoc.study_group.studyMember.domain.StudyMember;
import com.gdgoc.study_group.studyMember.domain.StudyMemberStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class RoundService {
  private final RoundRepository roundRepository;
  private final StudyRepository studyRepository;

  private void validateLeaderPermission(Long studyId, Long memberId) {
    StudyMember studyMember = roundRepository.findByStudyIdAndMemberId(studyId, memberId)
            .orElseThrow(() -> new CustomException(ErrorCode.STUDY_MEMBER_NOT_FOUND));

    if (studyMember.getStudyMemberStatus() != StudyMemberStatus.LEADER) {
      throw new CustomException(ErrorCode.NOT_STUDY_LEADER);
    }
  }

  public List<RoundResponse> getRoundsByStudyId(Long studyId) {
    if (!studyRepository.existsById(studyId)) {
      throw new CustomException(ErrorCode.STUDY_NOT_FOUND);
    }
    return roundRepository.findRoundsByStudyId(studyId).stream()
            .map(RoundResponse::from)
            .collect(Collectors.toList());
  }

  public RoundResponse getRound(Long roundId) {
    Round round = roundRepository.findById(roundId)
            .orElseThrow(() -> new CustomException(ErrorCode.ROUND_NOT_FOUND));
    return RoundResponse.from(round);
  }

  @Transactional
  public RoundResponse createRound(Long studyId, Long memberId, CreateRoundRequest request) {
    validateLeaderPermission(studyId, memberId);

    Study study = studyRepository.findById(studyId)
            .orElseThrow(() -> new CustomException(ErrorCode.STUDY_NOT_FOUND));

    if (request.date() == null) {
      throw new CustomException(ErrorCode.INVALID_ROUND_DATE);
    }

    Round round = Round.builder()
            .study(study)
            .goal(request.goal())
            .studyDetail(request.studyDetail())
            .roundDate(request.date())
            .roundStatus(request.roundStatus())
            .build();

    Round savedRound = roundRepository.save(round);
    return RoundResponse.from(savedRound);
  }

  @Transactional
  public RoundResponse updateRound(Long studyId, Long memberId, Long roundId, UpdateRoundRequest request) {
    validateLeaderPermission(studyId, memberId);

    Round round = roundRepository.findById(roundId)
            .orElseThrow(() -> new CustomException(ErrorCode.ROUND_NOT_FOUND));

    if (request.date() == null) {
      throw new CustomException(ErrorCode.INVALID_ROUND_DATE);
    }

    round.updateDetails(request.goal(), request.studyDetail(), request.date(), request.roundStatus());
    return RoundResponse.from(round);
  }

  @Transactional
  public void deleteRound(Long studyId, Long memberId, Long roundId) {
    validateLeaderPermission(studyId, memberId);

    Round round = roundRepository.findById(roundId)
            .orElseThrow(() -> new CustomException(ErrorCode.ROUND_NOT_FOUND));
    roundRepository.delete(round);
  }
}

