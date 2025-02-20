package com.gdgoc.study_group.round.dao;

import com.gdgoc.study_group.round.domain.Round;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import com.gdgoc.study_group.roundMember.domain.RoundMember;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
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


  // ================ ROUND MEMBER ================ //
  @Query("SELECT rm FROM RoundMember rm WHERE rm.round.id = :roundId AND rm.member.id = :memberId")
  Optional<RoundMember> findRoundMember(@Param(("roundId")) Long roundId, @Param("memberId") Long memberId);

  @Query("SELECT rm FROM RoundMember rm WHERE rm.round.id = :roundId")
  List<RoundMember> findRoundMemberByRoundId(@Param("roundId") Long roundId);

  @Modifying
  @Query(value = "INSERT INTO ROUND_MEMBER (round_id, member_id, retrospect) VALUES (:roundId, :memberId, :retrospect)", nativeQuery = true)
  void saveRoundMember(@Param("roundId") Long roundId, @Param("memberId") Long memberId, @Param("retrospect") String retrospect);

  @Modifying
  @Query("DELETE FROM RoundMember rm WHERE rm.id = :roundMemberId")
  void deleteRoundMemberById(@Param("roundMemberId") Long roundMemberId);
}
