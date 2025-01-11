package com.gdgoc.study_group.answer.repository;

import com.gdgoc.study_group.answer.domain.Answer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AnswerRepository extends JpaRepository<Answer, Long> {}
