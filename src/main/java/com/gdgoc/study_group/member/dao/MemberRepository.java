package com.gdgoc.study_group.member.dao;

import com.gdgoc.study_group.member.domain.Member;
import com.gdgoc.study_group.study.domain.Study;
import com.gdgoc.study_group.studyMember.domain.StudyMemberStatus;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {
  @Query("SELECT m FROM Member m WHERE m.name = :name AND "
          + "m.studentNumber = :studentNumber AND "
          + "m.github = :github")
  Member findMember(@Param("name") String name, @Param("studentNumber") String studentNumber,
                          @Param("github") String github);

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

  /**
   * 해당 멤버의 스터디에서의 상태를 조회합니다.
   *
   * @param memberId 검색할 멤버의 id
   * @param studyId 검색할 스터디의 id
   * @return 해당 스터디에서 멤버의 상태(Optional)
   */
  @Query(
      "SELECT sm.studyMemberStatus FROM StudyMember sm WHERE"
          + " sm.member.id = :memberId AND"
          + " sm.study.id = :studyId")
  Optional<StudyMemberStatus> findMemberStatus(
      @Param("memberId") Long memberId, @Param("studyId") Long studyId);

  /**
   * 특정 멤버의 특정 스터디에 대한 상태 조회
   *
   * @param memberId 멤버 아이디
   * @param studyId 스터디 아이디
   * @return 스터디 지원 상태, 지원한 이력이 없다면 {@code Optional.empty()}
   */
  @Query(
      "SELECT sm FROM StudyMember sm WHERE"
          + " sm.member.id = :memberId AND"
          + " sm.study.id = :studyId")
  Optional<StudyMemberStatus> findStudyMemberStatus(
      @Param("memberId") Long memberId, @Param("studyId") Long studyId);

  Member findByGithub(String github);
}
