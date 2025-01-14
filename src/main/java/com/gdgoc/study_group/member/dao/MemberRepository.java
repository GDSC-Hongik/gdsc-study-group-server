package com.gdgoc.study_group.member.dao;

import com.gdgoc.study_group.member.domain.Member;
import com.gdgoc.study_group.study.domain.Study;
import com.gdgoc.study_group.studyMember.domain.StudyMemberStatus;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {
  /**
   * 해당 멤버가 해당 상태를 가진 스터디 목록들을 반환합니다.
   *
   * @param memberId 검색할 대상이 되는 멤버의 PK
   * @param memberStatus 검색할 대상이 되는 멤버가 스터디에서 가지길 기대하는 상태
   * @return 해당 멤버가 포함되고, 해당 상태를 상태로 가지는 스터디들을 반환합니다.
   */
  @Query(
      "SELECT sm.study FROM StudyMember sm WHERE"
          + " sm.member.id = :id AND"
          + " sm.studyMemberStatus = :memberStatus")
  List<Study> findByMemberIdAndStatus(
      @Param("id") Long memberId, @Param("memberStatus") StudyMemberStatus memberStatus);

  Member findByGithub(String github);
}
