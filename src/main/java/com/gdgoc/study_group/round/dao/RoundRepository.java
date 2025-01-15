package com.gdgoc.study_group.round.dao;

import com.gdgoc.study_group.comment.domain.Comment;
import com.gdgoc.study_group.curriculum.domain.Curriculum;
import com.gdgoc.study_group.round.domain.Round;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface RoundRepository extends JpaRepository<Round, Long> {
  /**
   * 스터디의 모든 커리큘럼을 조회합니다
   *
   * @param studyId 스터디 아이디
   * @return 커리큘럼 List
   */
  @Query(value = "select c from Curriculum c where c.study.id = :studyId")
  List<Curriculum> findCurriculums(@Param("studyId") Long studyId);

  /**
   * 스터디의 특정 회차의 Curriculum 을 조회합니다
   *
   * @param studyId 스터디 아이디
   * @param week 조회할 회차
   * @return 해당 회차의 Curriculum
   */
  @Query("select c from Curriculum c where" + " c.study.id = :studyId AND" + " c.week = :week")
  Curriculum findCurriculumByWeek(@Param("studyId") Long studyId, @Param("week") Integer week);

  /**
   * 회차의 모든 댓글 조회
   *
   * @param roundId 라운드 아이디
   * @return 해당 라운드의 모든 댓글 리스트
   */
  @Query(value = "select c from Comment c where c.round.id = :roundId")
  List<Comment> findComments(@Param("roundId") Long roundId);
}
