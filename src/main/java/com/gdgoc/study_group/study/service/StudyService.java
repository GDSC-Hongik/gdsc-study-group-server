package com.gdgoc.study_group.study.service;

import com.gdgoc.study_group.study.domain.Status;
import com.gdgoc.study_group.study.domain.Study;
import com.gdgoc.study_group.study.dto.StudyCreateRequestDTO;
import com.gdgoc.study_group.study.dto.StudyListResponseDTO;
import com.gdgoc.study_group.study.repository.StudyRepository;
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

  // 전체 스터디 목록을 가져옴
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
}
