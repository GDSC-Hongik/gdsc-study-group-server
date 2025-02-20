package com.gdgoc.study_group.comment.application;

import com.gdgoc.study_group.comment.dao.CommentRepository;
import com.gdgoc.study_group.comment.domain.Comment;
import com.gdgoc.study_group.comment.dto.CommentResponse;
import com.gdgoc.study_group.exception.CustomException;
import com.gdgoc.study_group.member.dao.MemberRepository;
import com.gdgoc.study_group.member.domain.Member;
import com.gdgoc.study_group.round.dao.RoundRepository;
import com.gdgoc.study_group.round.domain.Round;
import com.gdgoc.study_group.comment.dto.CommentRequest;
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
    private final CommentRepository commentRepository;


    /**
     * 댓글을 작성합니다.
     *
     * @param roundId 댓글을 작성할 회차의 ID
     * @param memberId 댓글을 작성할 멤버의 ID
     * @param request 댓글 내용
     * @return 생성한 댓글의 ID
     */
    @Transactional(readOnly = false)
    public Long createComment(Long roundId, Long memberId, CommentRequest request) {

        Round round = roundRepository.findById(roundId).orElseThrow(() -> new CustomException(ROUND_NOT_FOUND));
        Member member = memberRepository.findById(memberId).orElseThrow(() -> new CustomException(MEMBER_NOT_FOUND));

        Comment comment = Comment.builder()
                .round(round)
                .member(member)
                .comment(request.comment())
                .build();

        commentRepository.save(comment);

        return comment.getId();
    }


    /**
     * 댓글을 수정합니다.
     *
     * @param roundId 수정할 댓글의 회차 ID
     * @param memberId 댓글을 작성한 멤버의 ID
     * @param commentId 수정할 댓글의 ID
     * @param request 수정할 댓글 내용
     */
    @Transactional(readOnly = false)
    public void updateComment(Long roundId, Long memberId, Long commentId, CommentRequest request) {

        Comment comment = commentRepository.findByRoundIdAndId(roundId, commentId)
                .orElseThrow(() -> new CustomException(COMMENT_NOT_FOUND));

        validateCommentOwner(comment, memberId);

        comment.update(request.comment());
    }


    /**
     * 댓글을 삭제합니다.
     *
     * @param roundId 삭제할 댓글의 회차 ID
     * @param memberId 댓글을 작성한 멤버의 ID
     * @param commentId 삭제할 댓글의 ID
     */
    @Transactional(readOnly = false)
    public void deleteComment(Long roundId, Long memberId, Long commentId) {

        Comment comment = commentRepository.findByRoundIdAndId(roundId, commentId)
                .orElseThrow(() -> new CustomException(COMMENT_NOT_FOUND));
        validateCommentOwner(comment, memberId);

        commentRepository.delete(comment);
    }


    /**
     * 댓글을 조회합니다.
     *
     * @param roundId 조회할 댓글의 회차 ID
     * @param commentId 조회할 댓글의 ID
     * @return 댓글 내용
     */
    public CommentResponse getComment(Long roundId, Long commentId) {

        return CommentResponse.from(commentRepository.findByRoundIdAndId(roundId, commentId)
                .orElseThrow(() -> new CustomException(COMMENT_NOT_FOUND)));
    }


    /**
     * 회차의 모든 댓글을 조회합니다.
     *
     * @param roundId 댓글을 조회할 회차의 ID
     * @return 회차의 모든 댓글
     */
    public List<CommentResponse> getAllComments(Long roundId) {

        return commentRepository.findAllByRoundId(roundId).stream().map(CommentResponse::from).toList();
    }


    //==========HELPER==========//
    private void validateCommentOwner(Comment comment, Long memberId) {
        if (!comment.getMember().getId().equals(memberId)) {
            throw new CustomException(FORBIDDEN);
        }
    }
}
