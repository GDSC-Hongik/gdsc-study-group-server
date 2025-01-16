package com.gdgoc.study_group.study.application;

import com.gdgoc.study_group.curriculum.domain.Curriculum;
import com.gdgoc.study_group.curriculum.dto.CurriculumDTO;
import com.gdgoc.study_group.day.domain.Day;
import com.gdgoc.study_group.day.dto.DayDTO;
import com.gdgoc.study_group.study.dao.StudyRepository;
import com.gdgoc.study_group.study.domain.Study;
import com.gdgoc.study_group.study.dto.StudyCreateRequest;
import com.gdgoc.study_group.study.dto.StudyCreateResponse;
import java.util.ArrayList;
import org.springframework.stereotype.Service;

@Service
public class StudyService {

  public final StudyRepository studyRepository;

  public StudyService(StudyRepository studyRepository) {
    this.studyRepository = studyRepository;
  }

  public StudyCreateResponse createStudy(StudyCreateRequest request) {

    // 스터디 생성
    Study study =
        Study.builder()
            .name(request.getName())
            .description(request.getDescription())
            .requirement(request.getRequirements())
            .question(request.getQuestion())
            .maxParticipants(request.getMaxParticipants())
            .studyStatus(request.getStudyStatus())
            .curriculums(new ArrayList<>()) // 빈 리스트로 초기화
            .days(new ArrayList<>()) // 빈 리스트로 초기화
            .isApplicationClosed(false) // 스터디 생성할 때 false로 설정
            .build();

    if (request.getCurriculums() != null) {
      for (CurriculumDTO curriculumDTO : request.getCurriculums()) {
        Curriculum curriculum =
            Curriculum.builder()
                .study(study)
                .week(curriculumDTO.getWeek())
                .subject(curriculumDTO.getSubject())
                .build();
        study.getCurriculums().add(curriculum);
      }
    }

    if (request.getDays() != null) {
      for (DayDTO dayDTO : request.getDays()) {
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

    // response DTO 생성
    StudyCreateResponse response =
        StudyCreateResponse.builder()
            .id(study.getId())
            .name(study.getName())
            .description(study.getDescription())
            .requirement(study.getRequirement())
            .maxParticipants(study.getMaxParticipants())
            .studyStatus(study.getStudyStatus())
            .curriculums(new ArrayList<>())
            .days(new ArrayList<>())
            .build();

    // response에 curriculum DTO 추가
    for (Curriculum curriculum : study.getCurriculums()) {
      CurriculumDTO curriculumDTO =
          CurriculumDTO.builder()
              .week(curriculum.getWeek())
              .subject(curriculum.getSubject())
              .build();
      response.getCurriculums().add(curriculumDTO);
    }

    // response에 day DTO 추가
    for (Day day : study.getDays()) {
      DayDTO dayDTO = DayDTO.builder().day(day.getDay()).startTime(day.getStartTime()).build();
      response.getDays().add(dayDTO);
    }

    return response;
  }
}
