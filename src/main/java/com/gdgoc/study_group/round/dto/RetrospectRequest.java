package com.gdgoc.study_group.round.dto;

import com.gdgoc.study_group.roundMember.domain.RoundMember;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

public record RetrospectRequest(
        @NotNull(message = "회고 내용을 입력해 주세요.") @Schema(description = "회고 내용") String retrospect) {

    public static RetrospectRequest from(RoundMember roundMember) {
        return new RetrospectRequest(roundMember.getRetrospect());
    }
}
