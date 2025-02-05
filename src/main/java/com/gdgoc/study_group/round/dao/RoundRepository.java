package com.gdgoc.study_group.round.dao;

import com.gdgoc.study_group.comment.domain.Comment;
import com.gdgoc.study_group.round.domain.Round;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface RoundRepository extends JpaRepository<Round, Long> {
  // ================ ROUND ================ //
  /**
   * 특정 스터디의 모든 회차를 조회합니다
   *
   * @param studyId 스터디 아이디
   * @return 해당 스터디의 모든 회차 List
   */
  @Query("SELECT r FROM Round r WHERE r.study.id = :studyId")
  List<Round> findRoundsByStudyId(@Param("studyId") Long studyId);

  /**
   * 특정 날짜에 진행되는 회차를 조회합니다
   *
   * @param roundDate 조회할 날짜
   * @return 해당 날짜에 진행되는 회차 List
   */
  @Query("SELECT r FROM Round r WHERE r.roundDate = :roundDate")
  List<Round> findRoundsByDate(@Param("roundDate") LocalDate roundDate);

  // ================ COMMENT ================ //
  /**
   * 회차의 모든 댓글 조회
   *
   * @param roundId 라운드 아이디
   * @return 해당 라운드의 모든 댓글 리스트
   */
  @Query("SELECT c FROM Comment c WHERE c.round.id = :roundId")
  List<Comment> findComments(@Param("roundId") Long roundId);

  @Query("SELECT c FROM Comment c WHERE c.round.id = :roundId AND" + " c.member.id = :memberId")
  Optional<Comment> findCommentByMemberId(
      @Param("roundId") Long roundId, @Param("memberId") Long memberId);
}
