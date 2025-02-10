package com.gdgoc.study_group.round.dao;

import com.gdgoc.study_group.comment.domain.Comment;
import com.gdgoc.study_group.roundMember.domain.RoundMember;

public interface RoundCustomRepository {

    // ========== COMMENT ========== //
    void saveComment(Comment comment);

    // ========== ROUND MEMBER ========== //
    void saveRoundMember(RoundMember roundMember);
}
