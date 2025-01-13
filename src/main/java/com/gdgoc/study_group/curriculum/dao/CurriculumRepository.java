package com.gdgoc.study_group.curriculum.repository;

import com.gdgoc.study_group.curriculum.domain.Curriculum;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CurriculumRepository extends CrudRepository<Curriculum, Long> {}
