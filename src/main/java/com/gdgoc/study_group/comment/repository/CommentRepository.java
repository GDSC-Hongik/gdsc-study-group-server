package com.gdgoc.study_group.comment.repository;

import com.gdgoc.study_group.comment.domain.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {
}
