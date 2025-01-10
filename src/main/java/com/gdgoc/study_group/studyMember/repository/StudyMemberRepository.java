package com.gdgoc.study_group.studyMember.repository;

import com.gdgoc.study_group.member.domain.Member;
import com.gdgoc.study_group.studyMember.domain.Status;
import com.gdgoc.study_group.studyMember.domain.StudyMember;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface StudyMemberRepository extends JpaRepository<StudyMember, Long> {

}
