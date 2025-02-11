package com.gdgoc.study_group.study.dao;

import com.gdgoc.study_group.answer.domain.Answer;
import com.gdgoc.study_group.curriculum.domain.Curriculum;
import com.gdgoc.study_group.day.domain.Day;
import com.gdgoc.study_group.study.domain.Study;
import com.gdgoc.study_group.study.domain.StudyStatus;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface StudyRepository extends JpaRepository<Study, Long> {
  // ================ STUDY ================ //
  /**
   * 해당하는 스터디 상태를 가진 모든 스터디를 조회합니다
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

  //  ================ CURRICULUM ================ //
  /**
   * 스터디의 모든 커리큘럼을 조회합니다
   *
   * @param studyId 스터디 아이디
   * @return 커리큘럼 List
   */
  @Query(value = "SELECT c FROM Curriculum c WHERE c.study.id = :studyId")
  List<Curriculum> findCurriculums(@Param("studyId") Long studyId);

  /**
   * 스터디의 특정 회차의 Curriculum 을 조회합니다
   *
   * @param studyId 스터디 아이디
   * @param week 조회할 회차
   * @return 해당 회차의 Curriculum
   */
  @Query("SELECT c FROM Curriculum c WHERE" + " c.study.id = :studyId AND" + " c.week = :week")
  Curriculum findCurriculumByWeek(@Param("studyId") Long studyId, @Param("week") Integer week);

  // ================ DAY ================ //
  /**
   * 해당 스터디의 날짜 정보를 조회합니다
   *
   * @param studyId 조회할 스터디의 Id
   * @return 해당 스터디의 주간 일정을 {@code List<Day> } 형식으로 반환
   */
  @Query(value = "SELECT d FROM Day d WHERE d.study.id = :studyId")
  List<Day> getStudyDay(@Param("studyId") Long studyId);

  // ================ ANSWER ================ //
  /**
   * 해당 스터디의 모든 답변을 조회합니다
   *
   * @param studyId 검색할 스터디의 id
   * @return 해당 스터디의 답변들을 반환
   */
  @Query("SELECT a FROM Answer a WHERE a.study.id = :studyId")
  List<Answer> findAnswers(@Param("studyId") Long studyId);

  /**
   * 특정 멤버의 스터디에 대한 답변들을 반환합니다
   * 일반적으로 1개이하여야 합니다
   * @param studyId
   * @param memberId
   * @return 해당하는 답변을 반환합니다. 일반적으로 1개이하입니다
   */
  @Query("SELECT a FROM Answer a WHERE a.study.id = :studyId AND "
          + "a.member.id = :memberId")
  List<Answer> findMemberAnswer(@Param("studyId") Long studyId, @Param("memberId") Long memberId);
}
