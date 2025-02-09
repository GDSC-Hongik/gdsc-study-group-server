package com.gdgoc.study_group.round.dao;

import com.gdgoc.study_group.comment.domain.Comment;

public interface RoundCustomRepository {

    void saveComment(Comment comment);
}
