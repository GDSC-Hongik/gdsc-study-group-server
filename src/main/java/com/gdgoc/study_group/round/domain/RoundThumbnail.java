package com.gdgoc.study_group.round.domain;

import com.gdgoc.study_group.member.domain.Member;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "ROUND_THUMBNAIL")
public class RoundThumbnail {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne
  @JoinColumn(name = "round_id")
  private Round round;

  @OneToOne
  @JoinColumn(name = "member_id")
  private Member member;

  private String fileName;
  private String filePath; // 파일의 이름을 제외한 저장 위치
  private String type; // jpeg, png...

  @Builder
  public RoundThumbnail(Round round, String fileName, String filePath, String type) {
    this.round = round;
    this.fileName = fileName;
    this.filePath = filePath;
    this.type = type;
  }
}