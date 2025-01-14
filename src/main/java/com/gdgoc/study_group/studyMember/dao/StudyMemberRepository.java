package com.gdgoc.study_group.studyMember.dao;

import com.gdgoc.study_group.studyMember.domain.Status;
import com.gdgoc.study_group.studyMember.domain.StudyMember;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudyMemberRepository extends JpaRepository<StudyMember, Long> {

  // 스터디에서 특정 역할의 멤버 조회
  List<StudyMember> findByStudyIdAndStatus(Long studyId, Status status);

  // 스터디에서 멤버의 상태 조회
  Optional<Status> findByMemberIdAndStudyId(Long memberId, Long studyId);

  // 해당 멤버가 속한 스터디 목록 조회(Status == LEADER || PARTICIPANT)
  List<StudyMember> findByMemberIdAndStatusIn(Long member_id, List<Status> statuses);
}
