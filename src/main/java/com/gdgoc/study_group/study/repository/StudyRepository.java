package com.gdgoc.study_group.study.repository;

import com.gdgoc.study_group.study.domain.Study;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StudyRepository extends JpaRepository<Study, Long> {

    List<Study>
}
