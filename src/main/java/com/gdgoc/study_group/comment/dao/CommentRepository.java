package com.gdgoc.study_group.comment.dao;

import com.gdgoc.study_group.comment.domain.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    /**
     * 회차의 모든 댓글을 조회합니다.
     *
     * @param roundId 라운드 아이디
     * @return 해당 라운드의 모든 댓글 리스트
     */
    @Query("SELECT c FROM Comment c WHERE c.round.id = :roundId")
    List<Comment> findAllByRoundId(@Param("roundId") Long roundId);


    /**
     * 특정 댓글을 조회합니다.
     * 주어진 Round ID와 Comment ID가 일치하는 댓글을 조회합니다.
     *
     * @param roundId 검증할 Round ID
     * @param commentId 조회할 Comment ID
     * @return 댓글이 존재할 경우 Optional에 담아 반환합니다. 아닐 경우, Optional.empty()를 반환합니다.
     */
    @Query("SELECT c FROM Comment c WHERE c.round.id = :roundId AND c.id = :commentId")
    Optional<Comment> findByRoundIdAndId(
            @Param("roundId") Long roundId, @Param("commentId") Long commentId);

}
