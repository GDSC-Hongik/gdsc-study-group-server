package com.gdgoc.study_group.day.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.gdgoc.study_group.study.domain.Study;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import java.time.LocalTime;
import lombok.*;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Day {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne
  @JoinColumn(name = "study_id", nullable = false)
  private Study study;

  private String day;

  @JsonFormat(pattern = "HH:mm")
  private LocalTime startTime;

  public static Day create(Study study, String day, LocalTime startTime) {
    return Day.builder().study(study).day(day).startTime(startTime).build();
  }
}
