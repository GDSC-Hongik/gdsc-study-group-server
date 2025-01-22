package com.gdgoc.study_group.member.domain;

import com.gdgoc.study_group.roundMember.domain.RoundMember;
import com.gdgoc.study_group.studyMember.domain.StudyMember;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;
import lombok.*;

@Entity
@Builder
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
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

  /**
   * 회원이 수정한 정보를 업데이트합니다.
   * @param name 회원 이름
   * @param github 회원 깃허브 주소
   * @param studentNumber 회원 학번
   */
  public void updateMember(String name, String github, String studentNumber) {

    this.name = name;
    this.github = github;
    this.studentNumber = studentNumber;
  }
}
