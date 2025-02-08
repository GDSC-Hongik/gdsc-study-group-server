package com.gdgoc.study_group.round.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.gdgoc.study_group.round.domain.Round;
import com.gdgoc.study_group.round.domain.RoundStatus;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

public record RoundDTO(
        Long id,
        int roundNumber,        // 회차 번호 추가
        String goal,
        String studyDetail,
        @JsonFormat(pattern = "yyyy-MM-dd")
        LocalDate roundDate,
        RoundStatus roundStatus,  // 상태 추가
        int participantCount,     // 참여자 수 추가
        List<RoundThumbnailDTO> thumbnails
) {
    public static RoundDTO from(Round round, int roundNumber) {
        return new RoundDTO(
                round.getId(),
                roundNumber,
                round.getGoal(),
                round.getStudyDetail(),
                round.getRoundDate(),
                round.getRoundStatus(),
                round.getRoundMembers().size(),
                round.getImages().stream()
                        .map(RoundThumbnailDTO::from)
                        .collect(Collectors.toList())
        );
    }
}