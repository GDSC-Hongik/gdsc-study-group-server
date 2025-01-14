package com.gdgoc.study_group.answer.dao;

import com.gdgoc.study_group.answer.domain.Answer;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AnswerRepository extends JpaRepository<Answer, Long> {

  boolean existsByMemberIdAndStudyId(Long memberId, Long studyId);

  // 스터디 신청 답변 조회
  List<Answer> findByStudyId(Long studyId);
}
