package com.gdgoc.study_group.comment.domain;

import com.gdgoc.study_group.member.domain.Member;
import com.gdgoc.study_group.round.domain.Round;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class Comment {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne
  @JoinColumn(name = "round_id")
  private Round round;

  @ManyToOne
  @JoinColumn(name = "member_id")
  private Member member;

  private String comment;

  public static Comment create(
          Round round,
          Member member,
          String comment) {
    return Comment.builder()
            .round(round)
            .member(member)
            .comment(comment)
            .build();
  }

  public void update(String comment) {
    this.comment = comment;
  }
}
