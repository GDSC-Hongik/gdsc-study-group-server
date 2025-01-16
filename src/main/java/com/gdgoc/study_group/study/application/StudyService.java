package com.gdgoc.study_group.study.application;

import com.gdgoc.study_group.curriculum.domain.Curriculum;
import com.gdgoc.study_group.curriculum.dto.CurriculumDTO;
import com.gdgoc.study_group.day.domain.Day;
import com.gdgoc.study_group.day.dto.DayDTO;
import com.gdgoc.study_group.study.dao.StudyRepository;
import com.gdgoc.study_group.study.domain.Study;
import com.gdgoc.study_group.study.dto.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;

/** 예외처리 + 권한 확인 아직 안 함.... 빨리 하겠슴다 */
@Service
public class StudyService {

  public final StudyRepository studyRepository;

  public StudyService(StudyRepository studyRepository) {
    this.studyRepository = studyRepository;
  }

  /**
   * 스터디를 생성합니다.
   *
   * @param createRequest 스터디 생성 DTO
   * @return ResponseDTO 반환
   */
  public StudyCreateResponse createStudy(StudyCreateRequest createRequest) {

    Study study =
        Study.builder()
            .name(createRequest.getName())
            .description(createRequest.getDescription())
            .requirement(createRequest.getRequirements())
            .question(createRequest.getQuestion())
            .maxParticipants(createRequest.getMaxParticipants())
            .studyStatus(createRequest.getStudyStatus())
            .curriculums(new ArrayList<>()) // 빈 리스트로 초기화
            .days(new ArrayList<>()) // 빈 리스트로 초기화
            .isApplicationClosed(false) // 스터디 생성할 때 false로 설정
            .build();

    if (createRequest.getCurriculums() != null) {
      for (CurriculumDTO curriculumDTO : createRequest.getCurriculums()) {
        Curriculum curriculum =
            Curriculum.builder()
                .study(study)
                .week(curriculumDTO.getWeek())
                .subject(curriculumDTO.getSubject())
                .build();
        study.getCurriculums().add(curriculum);
      }
    }

    if (createRequest.getDays() != null) {
      for (DayDTO dayDTO : createRequest.getDays()) {
        Day day =
            Day.builder()
                .study(study)
                .day(dayDTO.getDay())
                .startTime(dayDTO.getStartTime())
                .build();
        study.getDays().add(day);
      }
    }

    studyRepository.save(study);

    return StudyCreateResponse.builder().message("스터디가 생성되었습니다").id(study.getId()).build();
  }

  /**
   * 스터디 전체 목록을 조회합니다.
   *
   * @return 스터디 전체 목록 리스트
   */
  public List<StudyListResponse> getStudyList() {
    List<Study> studyList = studyRepository.findAll();
    List<StudyListResponse> listResponse = new ArrayList<>();

    for (Study study : studyList) {
      StudyListResponse studyListResponse =
          StudyListResponse.builder()
              .id(study.getId())
              .name(study.getName())
              .description(study.getDescription())
              .status(study.getStudyStatus())
              .build();
      listResponse.add(studyListResponse);
    }

    return listResponse;
  }

  /**
   * 스터디 상세 정보를 조회합니다.
   *
   * @param studyId 조회할 스터디 아이디
   * @return 스터디 정보 반환
   */
  public StudyDetailResponse getStudyDetail(Long studyId) {
    Optional<Study> study = studyRepository.findById(studyId);

    if (study.isPresent()) { // 해당 아이디를 가진 스터디가 존재할 때
      StudyDetailResponse detailResponse =
          StudyDetailResponse.builder()
              .id(studyId)
              .name(study.get().getName())
              .description(study.get().getDescription())
              .requirement(study.get().getRequirement())
              .question(study.get().getQuestion())
              .curriculums(new ArrayList<>())
              .days(new ArrayList<>())
              .maxParticipants(study.get().getMaxParticipants())
              .isApplicationClosed(study.get().getIsApplicationClosed())
              .build();

      // curriculum이 있다면 curriculumDTO로 변환해 response에 추가
      if (study.get().getCurriculums() != null) {
        for (Curriculum curriculum : study.get().getCurriculums()) {
          CurriculumDTO curriculumDTO =
              CurriculumDTO.builder()
                  .week(curriculum.getWeek())
                  .subject(curriculum.getSubject())
                  .build();
          detailResponse.getCurriculums().add(curriculumDTO);
        }
      }

      // day가 있다면 dayDTO로 변환해 response에 추가
      if (study.get().getDays() != null) {
        for (Day day : study.get().getDays()) {
          DayDTO dayDTO = DayDTO.builder().day(day.getDay()).startTime(day.getStartTime()).build();
          detailResponse.getDays().add(dayDTO);
        }
      }

      return detailResponse;
    }
    return null;
  }

  /**
   * 스터디장 권한 확인 필요 스터디를 삭제합니다
   *
   * @param studyId 삭제할 스터디의 아이디
   * @return 삭제 완료 메시지를 담은 MessageResponse DTO
   */
  public MessageResponse deleteStudy(Long studyId) {
    Optional<Study> study = studyRepository.findById(studyId);

    if (study.isEmpty()) {
      return null;
    }

    studyRepository.deleteById(studyId);
    return MessageResponse.builder().message("스터디가 삭제되었습니다.").build();
  }
}
