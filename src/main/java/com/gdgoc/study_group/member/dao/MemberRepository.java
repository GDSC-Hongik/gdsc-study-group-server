package com.gdgoc.study_group.member.dao;

import com.gdgoc.study_group.member.domain.Member;
import com.gdgoc.study_group.study.domain.StudyStatus;
import com.gdgoc.study_group.studyMember.domain.StudyMember;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {
  @Query(
      "SELECT s FROM StudyMember s WHERE s.member.id = :id AND s.studyMemberStatus = :studyStatus")
  List<StudyMember> findByMemberIdAndStatus(
      @Param("id") Long id, @Param("studyStatus") StudyStatus studyStatus);

  Member findByGithub(String github);
}
