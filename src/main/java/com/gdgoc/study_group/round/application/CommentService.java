package com.gdgoc.study_group.round.application;

import com.gdgoc.study_group.comment.domain.Comment;
import com.gdgoc.study_group.exception.CustomException;
import com.gdgoc.study_group.member.dao.MemberRepository;
import com.gdgoc.study_group.member.domain.Member;
import com.gdgoc.study_group.round.dao.RoundRepository;
import com.gdgoc.study_group.round.domain.Round;
import com.gdgoc.study_group.round.dto.CommentDTO;
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
     * @param memberId 댓글을 작성할 멤버의 ID
     * @param request 댓글 내용
     * @return 생성한 댓글의 ID
     */
    @Transactional(readOnly = false)
    public Long createComment(Long roundId, Long memberId, CommentDTO request) {

        Round round = roundRepository.findById(roundId).orElseThrow(() -> new CustomException(ROUND_NOT_FOUND));
        Member member = memberRepository.findById(memberId).orElseThrow(() -> new CustomException(MEMBER_NOT_FOUND));

        Comment comment = Comment.create(round, member, request.comment());
        roundRepository.saveComment(comment);

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
    public void updateComment(Long roundId, Long memberId, Long commentId, CommentDTO request) {

        Comment comment = roundRepository.findCommentByCommentId(roundId, commentId)
                .orElseThrow(() -> new CustomException(COMMENT_NOT_FOUND));

        System.out.println(comment.getMember().getId() + " AND " + memberId);
        validateCommentOwner(comment, memberId);

        comment.update(request);
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

        Comment comment = roundRepository.findCommentByCommentId(roundId, commentId)
                .orElseThrow(() -> new CustomException(COMMENT_NOT_FOUND));
        validateCommentOwner(comment, memberId);

        roundRepository.deleteCommentById(commentId);
    }


    /**
     * 댓글을 조회합니다.
     *
     * @param roundId 조회할 댓글의 회차 ID
     * @param commentId 조회할 댓글의 ID
     * @return 댓글 내용
     */
    public CommentDTO getComment(Long roundId, Long commentId) {

        return CommentDTO.from(roundRepository.findCommentByCommentId(roundId, commentId)
                .orElseThrow(() -> new CustomException(COMMENT_NOT_FOUND)));
    }


    /**
     * 회차의 모든 댓글을 조회합니다.
     *
     * @param roundId 댓글을 조회할 회차의 ID
     * @return 회차의 모든 댓글
     */
    public List<CommentDTO> getAllComments(Long roundId) {

        return roundRepository.findComments(roundId).stream().map(CommentDTO::from).toList();
    }


    //==========HELPER==========//
    private void validateCommentOwner(Comment comment, Long memberId) {
        if (!comment.getMember().getId().equals(memberId)) {
            throw new CustomException(FORBIDDEN);
        }
    }
}
