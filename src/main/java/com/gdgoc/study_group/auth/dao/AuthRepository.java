package com.gdgoc.study_group.auth.dao;

import com.gdgoc.study_group.auth.domain.Auth;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthRepository extends JpaRepository<Auth, Long> {

    Boolean existsByMember_StudentNumber(String studentNumber);
}