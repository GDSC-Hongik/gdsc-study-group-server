package com.gdgoc.study_group.roundMember.repository;

import com.gdgoc.study_group.roundMember.domain.RoundMember;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoundMemberRepository extends JpaRepository<RoundMember, Long> {
}
