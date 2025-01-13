package com.gdgoc.study_group.answer.repository;

import com.gdgoc.study_group.answer.domain.Answer;
import com.gdgoc.study_group.member.domain.Member;
import com.gdgoc.study_group.study.domain.Study;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AnswerRepository extends JpaRepository<Answer, Long> {

    boolean existsByMemberIdAndStudyId(Long memberId, Long studyId);

    // 스터디 신청 답변 조회
    List<Answer> findByStudyId(Long studyId);
}
