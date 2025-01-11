package com.gdgoc.study_group.study.service;

import com.gdgoc.study_group.study.domain.Study;
import com.gdgoc.study_group.study.dto.StudyDTO;
import com.gdgoc.study_group.study.repository.StudyRepository;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class StudyService {

  private final StudyRepository studyRepository;

  public StudyService(StudyRepository studyRepository) {
    this.studyRepository = studyRepository;
  }

  public Study createStudy(StudyDTO studyDTO) {
    Study study = new Study();
    study.setName(studyDTO.getName());
    study.setDescription(studyDTO.getDescription());
    study.setOffline(studyDTO.isOffline());
    study.setMaxParticipants(studyDTO.getMaxParticipants());

    return studyRepository.save(study);
  }

  public List<Study> getAllStudies() {
    List<Study> studies = studyRepository.findAll();

    return studies;
  }
}
