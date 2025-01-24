package com.gdgoc.study_group.round.repository;

import com.gdgoc.study_group.round.domain.RoundThumbnail;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface RoundThumbnailRepository extends JpaRepository<RoundThumbnail, Long> {

  // ================ ROUND THUMBNAIL ================ //
  /**
   * 특정 회차의 모든 썸네일을 조회합니다
   *
   * @param roundId 회차 아이디
   * @return 해당 회차의 모든 썸네일 List
   */
  @Query("SELECT rt FROM RoundThumbnail rt WHERE rt.round.id = :roundId")
  List<RoundThumbnail> findThumbnailsByRoundId(@Param("roundId") Long roundId);

  /**
   * 특정 회원이 업로드한 모든 썸네일을 조회합니다
   *
   * @param memberId 회원 아이디
   * @return 해당 회원이 업로드한 모든 썸네일 List
   */
  @Query("SELECT rt FROM RoundThumbnail rt WHERE rt.member.id = :memberId")
  List<RoundThumbnail> findThumbnailsByMemberId(@Param("memberId") Long memberId);
}
