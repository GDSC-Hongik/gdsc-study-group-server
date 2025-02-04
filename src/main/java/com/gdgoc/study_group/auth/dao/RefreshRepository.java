package com.gdgoc.study_group.auth.dao;

import com.gdgoc.study_group.auth.domain.Refresh;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RefreshRepository extends JpaRepository<Refresh, Long> {
}