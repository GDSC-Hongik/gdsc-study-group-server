package com.gdgoc.study_group.study.dao;

import com.gdgoc.study_group.answer.domain.Answer;
import com.gdgoc.study_group.study.domain.Study;
import com.gdgoc.study_group.study.domain.StudyStatus;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface StudyRepository extends JpaRepository<Study, Long> {
  /**
   * 해당 스터디의 모든 답변을 조회합니다
   *
   * @param studyId 검색할 스터디의 id
   * @return 해당 스터디의 답변들을 반환
   */
  @Query("SELECT a FROM Answer a WHERE a.study.id = :studyId")
  List<Answer> findAnswers(@Param("studyId") Long studyId);

  /**
   * 해당하는 상태를 가진 모든 스터디를 조회합니다
   *
   * @param studyStatus 조회를 원하는 스터디의 상태
   * @return 해당 상태를 가진 스터디 List
   */
  List<Study> findByStudyStatus(StudyStatus studyStatus);

  /**
   * 모집 중인 스터디 목록 조회
   *
   * @return 모집 중인 스터디 List
   */
  @Query("SELECT s FROM Study s WHERE s.isApplicationClosed = false")
  List<Study> findRecruitingStudy();
}
