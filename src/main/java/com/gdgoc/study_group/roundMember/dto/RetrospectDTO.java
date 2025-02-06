package com.gdgoc.study_group.roundMember.dto;

import com.gdgoc.study_group.roundMember.domain.RoundMember;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record RetrospectDTO(
        @NotBlank(message = "멤버 ID를 입력해 주세요.") @Schema(description = "회고를 입력할 멤버의 아이디") Long memberId,
        @NotNull(message = "회고 내용을 입력해 주세요.") @Schema(description = "회고 내용") String retrospect) {

    public static RetrospectDTO from(RoundMember roundMember) {
        return new RetrospectDTO(
                roundMember.getMember().getId(),
                roundMember.getRetrospect());
    }
}
