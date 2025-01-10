package com.gdgoc.study_group.round.repository;

import com.gdgoc.study_group.round.domain.Round;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoundRepository extends JpaRepository<Round, Long> {
}
