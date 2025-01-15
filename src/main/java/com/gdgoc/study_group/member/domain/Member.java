package com.gdgoc.study_group.member.domain;

import com.gdgoc.study_group.roundMember.domain.RoundMember;
import com.gdgoc.study_group.studyMember.domain.StudyMember;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
public class Member {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @OneToMany(mappedBy = "member")
  private List<StudyMember> studyMembers = new ArrayList<>();

  @OneToMany(mappedBy = "member")
  private List<RoundMember> roundMembers = new ArrayList<>();

  private String name;
  private String github;
  private String studentNumber;
}
