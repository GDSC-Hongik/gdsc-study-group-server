package com.gdgoc.study_group.answer.dao;

import com.gdgoc.study_group.answer.domain.Answer;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface AnswerRepository extends JpaRepository<Answer, Long> {

  /**
   * 특정 멤버가 특정 스터디에 지원한 내역이 있는지 확인
   *
   * @param memberId 멤버 아이디
   * @param studyId 스터디 아이디
   * @return 지원 여부
   */
  boolean existsByMemberIdAndStudyId(Long memberId, Long studyId);


  /**
   * 특정 스터디의 모든 답변 조회
   *
   * @param studyId 스터디 아이디
   * @return 등록된 모든 답변 리스트
   */
  @Query(value = "select a from Answer a " +
          "where a.study.id = :studyId")
  List<Answer> findByStudyId(@Param("studyId") Long studyId);


  /**
   * 특정 멤버가 작성한 모든 답변 조회
   *
   * @param memberId 멤버 아이디
   * @return 작성한 모든 답변 리스트
   */
  @Query(value = "select a from Answer a " +
          "where a.member.id = :memberId")
  List<Answer> findByMemberId(@Param("memberId") Long memberId)
}
