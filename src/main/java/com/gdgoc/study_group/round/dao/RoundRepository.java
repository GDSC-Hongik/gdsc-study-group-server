package com.gdgoc.study_group.round.dao;

import com.gdgoc.study_group.round.domain.Round;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoundRepository extends JpaRepository<Round, Long> {}
