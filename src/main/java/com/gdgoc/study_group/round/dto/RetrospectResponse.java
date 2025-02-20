package com.gdgoc.study_group.round.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.gdgoc.study_group.roundMember.domain.RoundMember;
import io.swagger.v3.oas.annotations.media.Schema;

public record RetrospectResponse(
        @Schema(description = "회고를 입력할 멤버의 아이디") @JsonProperty("member_id") Long memberId,
        @Schema(description = "회고 내용") String retrospect) {

    public static RetrospectResponse from(RoundMember roundMember) {
        return new RetrospectResponse(
                roundMember.getMember().getId(),
                roundMember.getRetrospect());
    }
}
