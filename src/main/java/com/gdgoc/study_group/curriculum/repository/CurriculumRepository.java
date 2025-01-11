package com.gdgoc.study_group.curriculum.repository;

import com.gdgoc.study_group.curriculum.domain.Curriculum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CurriculumRepository extends JpaRepository<Curriculum, Long> {}