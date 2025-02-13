package com.gdgoc.study_group.auth.dao;

import com.gdgoc.study_group.auth.domain.Auth;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthRepository extends JpaRepository<Auth, Long> {

    Boolean existsByMember_StudentNumber(String studentNumber);

    /* DB에서 studentNumber로 회원을 조회하는 메소드 */
    Auth findByMember_StudentNumber(String studentNumber);
}