package com.gdgoc.study_group.study.service;

import com.gdgoc.study_group.study.dao.StudyRepository;
import com.gdgoc.study_group.study.domain.Status;
import com.gdgoc.study_group.study.domain.Study;
import com.gdgoc.study_group.study.dto.StudyCreateRequestDTO;
import com.gdgoc.study_group.study.dto.StudyDetailResponseDTO;
import com.gdgoc.study_group.study.dto.StudyListResponseDTO;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class StudyService {

  private final StudyRepository studyRepository;

  public StudyService(StudyRepository studyRepository) {
    this.studyRepository = studyRepository;
  }

  // 스터디 생성
  public Study createStudy(StudyCreateRequestDTO studyCreateRequestDTO) {
    Study study = new Study();
    study.setName(studyCreateRequestDTO.getName());
    study.setDescription(studyCreateRequestDTO.getDescription());
    study.setMaxParticipants(studyCreateRequestDTO.getMaxParticipants());

    // 스터디 오프라인 여부 설정
    if (studyCreateRequestDTO.isOffline()) {
      study.setStatus(Status.OFFLINE);
    } else {
      study.setStatus(Status.ONLINE);
    }

    return studyRepository.save(study);
  }

  // 스터디 전체 목록 조회
  public List<StudyListResponseDTO> getStudyList() {
    List<Study> studyList = studyRepository.findAll();

    // StudyListResponseDTO로 변경
    List<StudyListResponseDTO> studyListResponseDTOList = new ArrayList<>();
    for (Study study : studyList) {
      StudyListResponseDTO studyListResponseDTO = new StudyListResponseDTO();

      studyListResponseDTO.setId(study.getId());
      studyListResponseDTO.setName(study.getName());
      studyListResponseDTO.setDescription(study.getDescription());
      studyListResponseDTO.setOffline(study.getStatus() == Status.OFFLINE);

      studyListResponseDTOList.add(studyListResponseDTO);
    }

    return studyListResponseDTOList;
  }

  // 스터디 상세 조회
  public StudyDetailResponseDTO getStudyDetail(Long studyId) {
    Study study = studyRepository.findById(studyId).orElse(null);
    StudyDetailResponseDTO studyDetailResponseDTO = new StudyDetailResponseDTO();

    if (study != null) { // 스터디가 존재하는 경우에만 DTO 설정
      studyDetailResponseDTO.setId(study.getId());
      studyDetailResponseDTO.setName(study.getName());
      studyDetailResponseDTO.setDescription(study.getDescription());
      studyDetailResponseDTO.setParticipants(study.getStudyMembers().size());
    }

    return studyDetailResponseDTO;
  }
}
