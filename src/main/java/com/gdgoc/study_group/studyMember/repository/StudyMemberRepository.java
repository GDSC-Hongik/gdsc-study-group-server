package com.gdgoc.study_group.studyMember.repository;

import com.gdgoc.study_group.studyMember.domain.StudyMember;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StudyMemberRepository extends JpaRepository<StudyMember, Long> {

    /**
     * 특정 스터디의 특정 멤버를 조회합니다.
     *
     * @param studyId 스터디 ID
     * @param memberId 멤버 ID
     * @return 스터디 멤버 Optional
     */
    @Query("SELECT sm FROM StudyMember sm WHERE sm.study.id = :studyId AND sm.member.id = :memberId")
    Optional<StudyMember> findByStudyIdAndMemberId(
            @Param("studyId") Long studyId,
            @Param("memberId") Long memberId
    );

    /**
     * 특정 스터디의 모든 멤버를 조회합니다.
     *
     * @param studyId 스터디 ID
     * @return 스터디 멤버 리스트
     */
    @Query("SELECT sm FROM StudyMember sm WHERE sm.study.id = :studyId")
    List<StudyMember> findAllByStudyId(@Param("studyId") Long studyId);

    /**
     * 특정 멤버가 속한 모든 스터디의 멤버십을 조회합니다.
     *
     * @param memberId 멤버 ID
     * @return 스터디 멤버 리스트
     */
    @Query("SELECT sm FROM StudyMember sm WHERE sm.member.id = :memberId")
    List<StudyMember> findAllByMemberId(@Param("memberId") Long memberId);

    /**
     * 특정 스터디의 리더를 조회합니다.
     *
     * @param studyId 스터디 ID
     * @return 리더인 스터디 멤버 Optional
     */
    @Query("SELECT sm FROM StudyMember sm WHERE sm.study.id = :studyId AND sm.studyMemberStatus = 'LEADER'")
    Optional<StudyMember> findLeaderByStudyId(@Param("studyId") Long studyId);
}