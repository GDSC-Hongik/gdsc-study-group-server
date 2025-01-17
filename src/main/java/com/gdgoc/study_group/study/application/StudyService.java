package com.gdgoc.study_group.study.application;

import com.gdgoc.study_group.curriculum.domain.Curriculum;
import com.gdgoc.study_group.curriculum.dto.CurriculumDTO;
import com.gdgoc.study_group.day.domain.Day;
import com.gdgoc.study_group.day.dto.DayDTO;
import com.gdgoc.study_group.member.dao.MemberRepository;
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

  public StudyService(StudyRepository studyRepository, MemberRepository memberRepository) {
    this.studyRepository = studyRepository;
  }

  private Curriculum createCurriculum(CurriculumDTO curriculumDTO, Study study) {
    return Curriculum.builder()
        .study(study)
        .week(curriculumDTO.getWeek())
        .subject(curriculumDTO.getSubject())
        .build();
  }

  private CurriculumDTO curriculumEntityToDTO(Curriculum curriculum) {
    return CurriculumDTO.builder()
        .week(curriculum.getWeek())
        .subject(curriculum.getSubject())
        .build();
  }

  private DayDTO dayEntityToDTO(Day day) {
    return DayDTO.builder().day(day.getDay()).startTime(day.getStartTime()).build();
  }

  private Day createDay(DayDTO dayDTO, Study study) {
    return Day.builder().study(study).day(dayDTO.getDay()).startTime(dayDTO.getStartTime()).build();
  }

  /**
   * 스터디를 생성합니다.
   *
   * @param createRequest 스터디 생성 DTO
   * @return ResponseDTO 반환
   */
  public StudyCreateResponse createStudy(Long userId, StudyCreateRequest createRequest) {

    Study study =
        Study.builder()
            .name(createRequest.getName())
            .description(createRequest.getDescription())
            .requirement(createRequest.getRequirements())
            .question(createRequest.getQuestion())
            .maxParticipants(createRequest.getMaxParticipants())
            .studyStatus(createRequest.getStudyStatus())
            .curriculums(new ArrayList<>()) // 빈 리스트로 초기화
            .days(new ArrayList<>())
            .studyMembers(new ArrayList<>())
            .isApplicationClosed(false) // 스터디 생성할 때 false로 설정
            .build();

    // 스터디를 생성한 유저를 스터디장으로 설정한 뒤 studyMembers에 추가
    // 하려고 하는데 아직 구현중

    // 등록된 커리큘럼이 있다면 엔티티로 변환하여 스터디에 추가
    if (createRequest.getCurriculums() != null) {
      for (CurriculumDTO curriculumDTO : createRequest.getCurriculums()) {
        study.getCurriculums().add(createCurriculum(curriculumDTO, study));
      }
    }

    // 등록된 스터디 날짜가 있다면 엔티티로 변환하여 스터디에 추가
    if (createRequest.getDays() != null) {
      for (DayDTO dayDTO : createRequest.getDays()) {
        study.getDays().add(createDay(dayDTO, study));
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
    Optional<Study> existingStudy = studyRepository.findById(studyId);

    if (existingStudy.isPresent()) { // 해당 아이디를 가진 스터디가 존재할 때
      Study study = existingStudy.get();

      StudyDetailResponse detailResponse =
          StudyDetailResponse.builder()
              .id(studyId)
              .name(study.getName())
              .description(study.getDescription())
              .requirement(study.getRequirement())
              .question(study.getQuestion())
              .curriculums(new ArrayList<>())
              .days(new ArrayList<>())
              .maxParticipants(study.getMaxParticipants())
              .isApplicationClosed(study.getIsApplicationClosed())
              .build();

      // curriculum이 있다면 curriculumDTO로 변환해 response에 추가
      if (study.getCurriculums() != null) {
        for (Curriculum curriculum : study.getCurriculums()) {
          detailResponse.getCurriculums().add(curriculumEntityToDTO(curriculum));
        }
      }

      // day가 있다면 dayDTO로 변환해 response에 추가
      if (study.getDays() != null) {
        for (Day day : study.getDays()) {
          detailResponse.getDays().add(dayEntityToDTO(day));
        }
      }

      return detailResponse;
    }
    return null;
  }

  public Long updateStudy(Long studyId, StudyCreateRequest updateRequest) {
    Optional<Study> study = studyRepository.findById(studyId);

    if (study.isEmpty()) {}

    Study existingStudy = studyRepository.findById(studyId).get();
    Study updatedStudy =
        Study.builder()
            .id(existingStudy.getId())
            .name(existingStudy.getName())
            .description(existingStudy.getDescription())
            .requirement(existingStudy.getRequirement())
            .question(existingStudy.getQuestion())
            .curriculums(new ArrayList<>())
            .days(new ArrayList<>())
            .maxParticipants(existingStudy.getMaxParticipants())
            .isApplicationClosed(existingStudy.getIsApplicationClosed())
            .build();

    // 등록된 커리큘럼이 있다면 엔티티로 변환하여 스터디에 추가
    if (updateRequest.getCurriculums() != null) {
      for (CurriculumDTO curriculumDTO : updateRequest.getCurriculums()) {
        updatedStudy.getCurriculums().add(createCurriculum(curriculumDTO, updatedStudy));
      }
    }

    // 등록된 스터디 날짜가 있다면 엔티티로 변환하여 스터디에 추가
    if (updatedStudy.getDays() != null) {
      for (DayDTO dayDTO : updateRequest.getDays()) {
        updatedStudy.getDays().add(createDay(dayDTO, updatedStudy));
      }
    }

    studyRepository.save(updatedStudy);

    return updatedStudy.getId();
  }

  /**
   * 스터디장 권한 확인 필요 스터디를 삭제합니다
   *
   * @param studyId 삭제할 스터디의 아이디
   * @return 해당하는 스터디의 존재 여부
   */
  public boolean deleteStudy(Long studyId) {
    Optional<Study> study = studyRepository.findById(studyId);

    if (study.isPresent()) {
      studyRepository.deleteById(studyId);
    }

    return study.isPresent();
  }
}
