package com.gdgoc.study_group.comment.dao;

import com.gdgoc.study_group.comment.domain.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {

    /**
     * 회차의 모든 댓글 조회
     *
     * @param roundId 라운드 아이디
     * @return 해당 라운드의 모든 댓글 리스트
     */
    @Query(value = "select c from Comment c " +
            "where c.round.id = :roundId")
    List<Comment> findAllComments(@Param("roundId") Long roundId);


    /**
     * 특정 멤버가 작성한 모든 댓글 조회
     *
     * @param memberId 멤버 아이디
     * @return 해당 멤버가 작성한 모든 댓글 리스트
     */
    @Query(value = "select c from Comment c " +
            "where c.member.id = :memberid")
    List<Comment> findCommentByMemberId(@Param("memberId") Long memberId);
}
