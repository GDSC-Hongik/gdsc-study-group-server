package com.gdgoc.study_group.round.service;

import com.gdgoc.study_group.round.domain.Round;
import com.gdgoc.study_group.round.domain.RoundThumbnail;
import com.gdgoc.study_group.round.dto.CreateRoundRequest;
import com.gdgoc.study_group.round.dto.RoundDTO;
import com.gdgoc.study_group.round.dto.RoundThumbnailDTO;
import com.gdgoc.study_group.round.dto.UpdateRoundRequest;
import com.gdgoc.study_group.round.repository.RoundRepository;
import com.gdgoc.study_group.round.repository.RoundThumbnailRepository;
import com.gdgoc.study_group.study.dao.StudyRepository;
import com.gdgoc.study_group.study.domain.Study;
import com.gdgoc.study_group.studyMember.domain.StudyMember;
import com.gdgoc.study_group.studyMember.domain.StudyMemberStatus;
import com.gdgoc.study_group.studyMember.repository.StudyMemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class RoundService {
  private final RoundRepository roundRepository;
  private final RoundThumbnailRepository roundThumbnailRepository;
  private final StudyRepository studyRepository;
  private final StudyMemberRepository studyMemberRepository;

  private void validateLeaderPermission(Long studyId, Long memberId) {
    StudyMember studyMember = studyMemberRepository.findByStudyIdAndMemberId(studyId, memberId)
            .orElseThrow(() -> new IllegalArgumentException("Study member not found"));

    if (studyMember.getStudyMemberStatus() != StudyMemberStatus.LEADER) {
      throw new IllegalArgumentException("Only leader can perform this operation");
    }
  }

  public List<RoundDTO> getRoundsByStudyId(Long studyId) {
    List<Round> rounds = roundRepository.findRoundsByStudyId(studyId);
    return IntStream.range(0, rounds.size())
            .mapToObj(i -> RoundDTO.from(rounds.get(i), i + 1))
            .collect(Collectors.toList());
  }

  public RoundDTO getRound(Long roundId) {
    Round round = roundRepository.findById(roundId)
            .orElseThrow(() -> new IllegalArgumentException("Round not found"));

    // 해당 라운드의 번호를 조회
    List<Round> allRounds = roundRepository.findRoundsByStudyId(round.getStudy().getId());
    int roundNumber = IntStream.range(0, allRounds.size())
            .filter(i -> allRounds.get(i).getId().equals(roundId))
            .findFirst()
            .orElse(0) + 1;

    return RoundDTO.from(round, roundNumber);
  }

  @Transactional
  public RoundDTO createRound(Long studyId, Long memberId, CreateRoundRequest request) {
    validateLeaderPermission(studyId, memberId);

    Study study = studyRepository.findById(studyId)
            .orElseThrow(() -> new IllegalArgumentException("Study not found"));

    Round round = Round.builder()
            .study(study)
            .goal(request.goal())
            .studyDetail(request.studyDetail())
            .roundDate(request.roundDate())
            .roundStatus(request.roundStatus())
            .build();

    Round savedRound = roundRepository.save(round);

    if (request.thumbnails() != null && !request.thumbnails().isEmpty()) {
      List<RoundThumbnail> thumbnails = request.thumbnails().stream()
              .map(thumbnailDTO -> RoundThumbnail.builder()
                      .round(savedRound)
                      .fileName(thumbnailDTO.fileName())
                      .filePath(thumbnailDTO.filePath())
                      .type(thumbnailDTO.type())
                      .build())
              .collect(Collectors.toList());

      roundThumbnailRepository.saveAll(thumbnails);
    }

    return RoundDTO.from(savedRound, getRoundNumber(savedRound));
  }

  @Transactional
  public RoundDTO updateRound(Long studyId, Long memberId, Long roundId, UpdateRoundRequest request) {
    validateLeaderPermission(studyId, memberId);

    Round round = roundRepository.findById(roundId)
            .orElseThrow(() -> new IllegalArgumentException("Round not found"));

    round.updateDetails(request.goal(), request.studyDetail(), request.roundDate(), request.roundStatus());
    Round updatedRound = roundRepository.save(round);

    if (request.thumbnails() != null && !request.thumbnails().isEmpty()) {
      List<RoundThumbnail> thumbnails = request.thumbnails().stream()
              .map(thumbnailDTO -> RoundThumbnail.builder()
                      .round(updatedRound)
                      .fileName(thumbnailDTO.fileName())
                      .filePath(thumbnailDTO.filePath())
                      .type(thumbnailDTO.type())
                      .build())
              .collect(Collectors.toList());

      roundThumbnailRepository.saveAll(thumbnails);
    }

    return RoundDTO.from(updatedRound, getRoundNumber(updatedRound));
  }

  @Transactional
  public void deleteRound(Long studyId, Long memberId, Long roundId) {
    validateLeaderPermission(studyId, memberId);

    Round round = roundRepository.findById(roundId)
            .orElseThrow(() -> new IllegalArgumentException("Round not found"));
    roundRepository.delete(round);
  }

  private int getRoundNumber(Round round) {
    List<Round> allRounds = roundRepository.findRoundsByStudyId(round.getStudy().getId());
    return IntStream.range(0, allRounds.size())
            .filter(i -> allRounds.get(i).getId().equals(round.getId()))
            .findFirst()
            .orElse(0) + 1;
  }
}