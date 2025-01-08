package com.gdgoc.study_group.roundMember.domain;

import com.gdgoc.study_group.member.domain.Member;
import com.gdgoc.study_group.round.domain.Round;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "ROUND_MEMBER")
public class RoundMember {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne
  @JoinColumn(name = "round_id")
  private Round round;

  @ManyToOne
  @JoinColumn(name = "member_id")
  private Member member;

  private String retrospect; // 후기 내용
}
