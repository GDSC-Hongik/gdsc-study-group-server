package com.gdgoc.study_group.study.dao;

import com.gdgoc.study_group.study.domain.Study;
import com.gdgoc.study_group.study.domain.StudyStatus;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudyRepository extends JpaRepository<Study, Long> {

  // 특정 Status인 스터디 목록 조회
  List<Study> findByStatus(StudyStatus studyStatus);

  // 모집 중인 스터디 목록 조회(status != FINISHED)
  List<Study> findByStatusNot(StudyStatus studyStatus);
}
