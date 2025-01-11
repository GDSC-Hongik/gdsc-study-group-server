package com.gdgoc.study_group.studyMember.repository;

import com.gdgoc.study_group.member.domain.Member;
import com.gdgoc.study_group.studyMember.domain.Status;
import com.gdgoc.study_group.studyMember.domain.StudyMember;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudyMemberRepository extends JpaRepository<StudyMember, Long> {

  // 멤버 아이디와 역할로 멤버 검색
  Optional<Member> findByMemberIdAndStatus(Long member_id, Status status);

  // 멤버 아이디와 스터디 아이디로 멤버 검색
  Optional<Member> findByMemberIdAndStudyId(Long member_id, Long studyId);
}
