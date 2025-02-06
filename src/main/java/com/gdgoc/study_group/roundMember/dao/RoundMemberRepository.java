package com.gdgoc.study_group.roundMember.dao;

import com.gdgoc.study_group.roundMember.domain.RoundMember;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoundMemberRepository extends JpaRepository<RoundMember, Long> {

    /**
     * roundId와 일치하는 모든 회고를 조회합니다.
     *
     * @param roundId 조회할 라운드의 아이디
     * @return 해당 라운드의 모든 회고
     */
    @Query("SELECT rm FROM RoundMember rm WHERE rm.round.id = :roundId")
    List<RoundMember> findRoundMemberByRoundId(@Param("roundId") Long roundId);
}
