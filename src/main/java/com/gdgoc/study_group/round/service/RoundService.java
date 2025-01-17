package com.gdgoc.study_group.round.service;

import com.gdgoc.study_group.round.domain.Round;
import com.gdgoc.study_group.round.domain.RoundThumbnail;
import com.gdgoc.study_group.round.dto.RoundDTO;
import com.gdgoc.study_group.round.dto.RoundThumbnailDTO;
import com.gdgoc.study_group.round.repository.RoundRepository;
import com.gdgoc.study_group.round.repository.RoundThumbnailRepository;
import com.gdgoc.study_group.study.dao.StudyRepository;
import com.gdgoc.study_group.study.domain.Study;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class RoundService {

  private final RoundRepository roundRepository;
  private final RoundThumbnailRepository roundThumbnailRepository;
  private final StudyRepository studyRepository;

  public List<RoundDTO> getRoundsByStudyId(Long studyId) {
    return roundRepository.findRoundsByStudyId(studyId)
            .stream()
            .map(round -> new RoundDTO(
                    round.getId(),
                    round.getGoal(),
                    round.getStudyDetail(),
                    round.getRoundDate(),
                    round.getImages().stream()
                            .map(image -> new RoundThumbnailDTO(
                                    image.getId(),
                                    image.getFileName(),
                                    image.getFilePath(),
                                    image.getType()))
                            .collect(Collectors.toList())))
            .collect(Collectors.toList());
  }

  public RoundDTO getRound(Long roundId) {
    Round round = roundRepository.findById(roundId)
            .orElseThrow(() -> new IllegalArgumentException("Round not found"));
    return new RoundDTO(
            round.getId(),
            round.getGoal(),
            round.getStudyDetail(),
            round.getRoundDate(),
            round.getImages().stream()
                    .map(image -> new RoundThumbnailDTO(
                            image.getId(),
                            image.getFileName(),
                            image.getFilePath(),
                            image.getType()))
                    .collect(Collectors.toList()));
  }

  public RoundDTO createRound(Long studyId, String goal, String studyDetail, LocalDate roundDate, List<RoundThumbnailDTO> thumbnails) {
    Study study = studyRepository.findById(studyId)
            .orElseThrow(() -> new IllegalArgumentException("Study not found"));

    Round round = Round.builder()
            .study(study)
            .goal(goal)
            .studyDetail(studyDetail)
            .roundDate(roundDate)
            .build();

    Round savedRound = roundRepository.save(round);

    List<RoundThumbnail> savedThumbnails = thumbnails.stream()
            .map(thumbnailDTO -> RoundThumbnail.builder()
                    .round(savedRound)
                    .fileName(thumbnailDTO.fileName())
                    .filePath(thumbnailDTO.filePath())
                    .type(thumbnailDTO.type())
                    .build())
            .collect(Collectors.toList());

    roundThumbnailRepository.saveAll(savedThumbnails);

    return new RoundDTO(
            savedRound.getId(),
            savedRound.getGoal(),
            savedRound.getStudyDetail(),
            savedRound.getRoundDate(),
            savedThumbnails.stream()
                    .map(thumbnail -> new RoundThumbnailDTO(
                            thumbnail.getId(),
                            thumbnail.getFileName(),
                            thumbnail.getFilePath(),
                            thumbnail.getType()))
                    .collect(Collectors.toList()));
  }

  public RoundDTO updateRound(Long roundId, String goal, String studyDetail, LocalDate roundDate, List<RoundThumbnailDTO> thumbnails) {
    Round round = roundRepository.findById(roundId)
            .orElseThrow(() -> new IllegalArgumentException("Round not found"));

    round.updateDetails(goal, studyDetail, roundDate);
    Round updatedRound = roundRepository.save(round);

    // Update thumbnails
    List<RoundThumbnail> savedThumbnails = thumbnails.stream()
            .map(thumbnailDTO -> RoundThumbnail.builder()
                    .round(updatedRound)
                    .fileName(thumbnailDTO.fileName())
                    .filePath(thumbnailDTO.filePath())
                    .type(thumbnailDTO.type())
                    .build())
            .collect(Collectors.toList());

    roundThumbnailRepository.saveAll(savedThumbnails);

    return new RoundDTO(
            updatedRound.getId(),
            updatedRound.getGoal(),
            updatedRound.getStudyDetail(),
            updatedRound.getRoundDate(),
            savedThumbnails.stream()
                    .map(thumbnail -> new RoundThumbnailDTO(
                            thumbnail.getId(),
                            thumbnail.getFileName(),
                            thumbnail.getFilePath(),
                            thumbnail.getType()))
                    .collect(Collectors.toList()));
  }

  public void deleteRound(Long roundId) {
    Round round = roundRepository.findById(roundId)
            .orElseThrow(() -> new IllegalArgumentException("Round not found"));
    roundRepository.delete(round);
  }
}