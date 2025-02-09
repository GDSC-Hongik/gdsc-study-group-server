package com.gdgoc.study_group.round.application;

import com.gdgoc.study_group.comment.domain.Comment;
import com.gdgoc.study_group.comment.dto.CommentDto;
import com.gdgoc.study_group.exception.CustomException;
import com.gdgoc.study_group.member.dao.MemberRepository;
import com.gdgoc.study_group.member.domain.Member;
import com.gdgoc.study_group.round.dao.RoundRepository;
import com.gdgoc.study_group.round.domain.Round;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.gdgoc.study_group.exception.ErrorCode.*;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CommentService {

    private final RoundRepository roundRepository;
    private final MemberRepository memberRepository;


    /**
     * 댓글을 작성합니다.
     *
     * @param roundId 댓글을 작성할 회차의 ID
     * @param request 멤버 아이디와 댓글 내용
     * @return 생성한 댓글의 ID
     */
    @Transactional(readOnly = false)
    public Long createComment(Long roundId, CommentDto request) {

        Round round = roundRepository.findById(roundId).orElseThrow(() -> new CustomException(ROUND_NOT_FOUND));
        Member member = memberRepository.findById(request.memberId()).orElseThrow(() -> new CustomException(MEMBER_NOT_FOUND));

        Comment comment = Comment.create(round, member, request.comment());
        roundRepository.saveComment(comment);

        return comment.getId();
    }


    /**
     * 댓글을 수정합니다.
     *
     * @param roundId 수정할 댓글의 회차 ID
     * @param commentId 수정할 댓글의 ID
     * @param updatedComment 수정할 댓글 내용
     */
    @Transactional(readOnly = false)
    public void updateComment(Long roundId, Long commentId, String updatedComment) {

        Comment comment = roundRepository.findCommentByCommentId(roundId, commentId).orElseThrow(() -> new CustomException(COMMENT_NOT_FOUND));
        comment.update(updatedComment);
    }


    /**
     * 댓글을 삭제합니다.
     *
     * @param roundId 삭제할 댓글의 회차 ID
     * @param commentId 삭제할 댓글의 ID
     */
    @Transactional(readOnly = false)
    public void deleteComment(Long roundId, Long commentId) {

        roundRepository.findCommentByCommentId(roundId, commentId).orElseThrow(() -> new CustomException(COMMENT_NOT_FOUND));
        roundRepository.deleteCommentById(commentId);
    }


    /**
     * 댓글을 조회합니다.
     *
     * @param roundId 조회할 댓글의 회차 ID
     * @param commentId 조회할 댓글의 ID
     * @return 댓글 내용
     */
    public CommentDto getComment(Long roundId, Long commentId) {

        Comment comment = roundRepository.findCommentByCommentId(roundId, commentId).orElseThrow(() -> new CustomException(COMMENT_NOT_FOUND));
        return CommentDto.from(comment);
    }


    /**
     * 회차의 모든 댓글을 조회합니다.
     *
     * @param roundId 댓글을 조회할 회차의 ID
     * @return 회차의 모든 댓글
     */
    public List<CommentDto> getAllComments(Long roundId) {

        roundRepository.findById(roundId).orElseThrow(() -> new CustomException(ROUND_NOT_FOUND));
        return roundRepository.findComments(roundId).stream().map(CommentDto::from).toList();
    }
}
