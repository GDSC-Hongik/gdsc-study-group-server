package com.gdgoc.study_group.member.dao;

import com.gdgoc.study_group.member.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {

  Member findByGithub(String github);
}
