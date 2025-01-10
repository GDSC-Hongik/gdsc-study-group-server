package com.gdgoc.study_group.member.repository;

import com.gdgoc.study_group.member.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {
}
