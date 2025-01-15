package com.gdgoc.study_group.round.dao;

import com.gdgoc.study_group.comment.domain.Comment;
import com.gdgoc.study_group.round.domain.Round;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface RoundRepository extends JpaRepository<Round, Long> {
  // ================ COMMENT ================ //
  /**
   * 회차의 모든 댓글 조회
   *
   * @param roundId 라운드 아이디
   * @return 해당 라운드의 모든 댓글 리스트
   */
  @Query("select c from Comment c where c.round.id = :roundId")
  List<Comment> findComments(@Param("roundId") Long roundId);

  @Query("SELECT c FROM Comment c WHERE c.round.id = :roundId AND" + " c.member.id = :memberId")
  Optional<Comment> findCommentByMemberId(
      @Param("roundId") Long roundId, @Param("memberId") Long memberId);
}
