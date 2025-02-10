package com.gdgoc.study_group.round.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.gdgoc.study_group.roundMember.domain.RoundMember;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record RetrospectResponse(
        @NotBlank(message = "멤버 ID를 입력해 주세요.") @Schema(description = "회고를 입력할 멤버의 아이디") @JsonProperty("member_id") Long memberId,
        @NotNull(message = "회고 내용을 입력해 주세요.") @Schema(description = "회고 내용") String retrospect) {

    public static RetrospectResponse from(RoundMember roundMember) {
        return new RetrospectResponse(
                roundMember.getMember().getId(),
                roundMember.getRetrospect());
    }
}
