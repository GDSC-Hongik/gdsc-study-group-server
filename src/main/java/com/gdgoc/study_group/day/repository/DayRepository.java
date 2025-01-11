package com.gdgoc.study_group.day.repository;

import com.gdgoc.study_group.day.domain.Day;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DayRepository extends JpaRepository<Day, Long> {}
