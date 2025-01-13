package com.gdgoc.study_group.day.repository;

import com.gdgoc.study_group.day.dao.Day;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DayRepository extends CrudRepository<Day, Long> {}
