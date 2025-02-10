package com.gdgoc.study_group.round.application;

import com.gdgoc.study_group.round.domain.Round;
import com.gdgoc.study_group.round.domain.RoundThumbnail;
import com.gdgoc.study_group.round.dto.CreateRoundRequest;
import com.gdgoc.study_group.round.dto.RoundDTO;
import com.gdgoc.study_group.round.dto.UpdateRoundRequest;
import com.gdgoc.study_group.round.dao.RoundRepository;
import com.gdgoc.study_group.round.dao.RoundThumbnailRepository;
import com.gdgoc.study_group.study.dao.StudyRepository;
import com.gdgoc.study_group.study.domain.Study;
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
  private final RoundThumbnailRepository roundThumbnailRepository;
  private final StudyRepository studyRepository;

  public List<RoundDTO> getRoundsByStudyId(Long studyId) {
    return roundRepository.findRoundsByStudyId(studyId)
            .stream()
            .map(RoundDTO::from)
            .collect(Collectors.toList());
  }

  public RoundDTO getRound(Long roundId) {
    Round round = roundRepository.findById(roundId)
            .orElseThrow(() -> new IllegalArgumentException("Round not found"));
    return RoundDTO.from(round);
  }

  @Transactional
  public RoundDTO createRound(Long studyId, CreateRoundRequest request) {
    Study study = studyRepository.findById(studyId)
            .orElseThrow(() -> new IllegalArgumentException("Study not found"));

    Round round = Round.builder()
            .study(study)
            .goal(request.goal())
            .studyDetail(request.studyDetail())
            .roundDate(request.roundDate())
            .build();

    Round savedRound = roundRepository.save(round);

    List<RoundThumbnail> savedThumbnails = request.thumbnails().stream()
            .map(thumbnailDTO -> RoundThumbnail.builder()
                    .round(savedRound)
                    .fileName(thumbnailDTO.fileName())
                    .filePath(thumbnailDTO.filePath())
                    .type(thumbnailDTO.type())
                    .build())
            .collect(Collectors.toList());

    roundThumbnailRepository.saveAll(savedThumbnails);
    return RoundDTO.from(savedRound);
  }

  @Transactional
  public RoundDTO updateRound(Long roundId, UpdateRoundRequest request) {
    Round round = roundRepository.findById(roundId)
            .orElseThrow(() -> new IllegalArgumentException("Round not found"));

    round.updateDetails(request.goal(), request.studyDetail(), request.roundDate());
    Round updatedRound = roundRepository.save(round);

    List<RoundThumbnail> savedThumbnails = request.thumbnails().stream()
            .map(thumbnailDTO -> RoundThumbnail.builder()
                    .round(updatedRound)
                    .fileName(thumbnailDTO.fileName())
                    .filePath(thumbnailDTO.filePath())
                    .type(thumbnailDTO.type())
                    .build())
            .collect(Collectors.toList());

    roundThumbnailRepository.saveAll(savedThumbnails);
    return RoundDTO.from(updatedRound);
  }

  @Transactional
  public void deleteRound(Long roundId) {
    Round round = roundRepository.findById(roundId)
            .orElseThrow(() -> new IllegalArgumentException("Round not found"));
    roundRepository.delete(round);
  }
}