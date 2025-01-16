package com.gdgoc.study_group.study.application;

import com.gdgoc.study_group.curriculum.domain.Curriculum;
import com.gdgoc.study_group.curriculum.dto.CurriculumDTO;
import com.gdgoc.study_group.day.domain.Day;
import com.gdgoc.study_group.day.dto.DayDTO;
import com.gdgoc.study_group.study.dao.StudyRepository;
import com.gdgoc.study_group.study.domain.Study;
import com.gdgoc.study_group.study.dto.StudyCreateRequest;
import com.gdgoc.study_group.study.dto.StudyCreateResponse;
import com.gdgoc.study_group.study.dto.StudyDetailResponse;
import com.gdgoc.study_group.study.dto.StudyListResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;

@Service
public class StudyService {

  public final StudyRepository studyRepository;

  public StudyService(StudyRepository studyRepository) {
    this.studyRepository = studyRepository;
  }

  public StudyCreateResponse createStudy(StudyCreateRequest createRequest) {

    // 스터디 생성
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

    return StudyCreateResponse.builder().message("Study Created").id(study.getId()).build();
  }

  // 스터디 전체 목록 조회
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

  // 스터디 상세 정보 조회
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

      // day가 있다면 dayDTO로 변환해 responsedp cnrk
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
}
