package com.gdgoc.study_group.comment.repository;

import com.gdgoc.study_group.comment.dao.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {}
