package com.gdgoc.study_group.auth.dao;

import com.gdgoc.study_group.auth.domain.Auth;
import com.gdgoc.study_group.auth.domain.Refresh;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
public interface RefreshRepository extends JpaRepository<Refresh, Long> {

    @Transactional(readOnly = false)
    void deleteByAuth(Auth auth);

    Boolean existsByRefresh(String refresh);

    @Transactional(readOnly = false)
    void deleteByRefresh(String refresh);
}