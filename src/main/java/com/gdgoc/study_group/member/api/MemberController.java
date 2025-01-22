package com.gdgoc.study_group.member.api;

import com.gdgoc.study_group.member.application.MemberService;
import com.gdgoc.study_group.member.domain.Member;
import com.gdgoc.study_group.member.dto.request.MemberRequestDto;
import com.gdgoc.study_group.member.dto.response.MemberCreateResponseDto;
import com.gdgoc.study_group.member.dto.response.MemberGetResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
@Tag(name = "Member", description = "회원 API")
public class MemberController {
    private final MemberService memberService;

    /**
     * 새 멤버를 생성합니다.
     *
     * @param request 새로 만들 멤버 정보
     * @return 성공하면 생성 성공 메시지와 생성된 회원의 ID 반환
     */
    @Operation(summary = "멤버 생성", description = "관리자가 직접 정보 넣어서 멤버 생성")
    @PostMapping
    public ResponseEntity<MemberCreateResponseDto> createMember(@RequestBody MemberRequestDto request) {
        Member createdMember = memberService.createMember(request);

        // record 생성자 직접 호출
        MemberCreateResponseDto response = new MemberCreateResponseDto(createdMember.getId().toString());

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    /**
     * 멤버를 조회합니다.
     *
     * @param memberId 멤버를 조회할 정보
     * @return 성공하면 성공 메시지와 회원 정보 반환
     */
    @Operation(summary = "멤버 조회", description = "url에 담긴 id 정보로 해당 회원 정보 조회")
    @GetMapping("/{id}/profile")
    public ResponseEntity<MemberGetResponseDto> getMember(
            @PathVariable("id") Long memberId
//            @RequestHeader("Authorization") String authorizationHeader
    ) {
//        // Bearer 토큰 추출
//        String token = authorizationHeader.replace("Bearer ", "");
//
//        // TODO: JWT 토큰 검증 로직 추가

        Member member = memberService.getMember(memberId);

        // record 생성자 직접 호출
        MemberGetResponseDto response = new MemberGetResponseDto(
                member.getId().toString(),
                member.getName(),
                member.getGithub(),
                member.getStudentNumber()
        );

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    /**
     * 멤버가 정보를 수정합니다.
     *
     * @param memberId 멤버를 조회할 정보
     * @param request 수정할 정보
     * @return 성공하면 성공 메시지 204 반환
     */
    @Operation(summary = "멤버 정보 수정", description = "url에 담긴 id 정보로 해당 회원 정보 수정")
    @PatchMapping("/{id}/profile")
    public ResponseEntity<String> updateMemberProfile(
            @PathVariable("id") Long memberId,
            @RequestBody MemberRequestDto request
//            @RequestHeader("Authorization") String authorizationHeader
    ) {
//        // Bearer 토큰 추출
//        String token = authorizationHeader.replace("Bearer ", "");
//
//        // TODO: JWT 토큰 검증 로직 추가

        memberService.updateMember(memberId, request);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("프로필 수정 성공");
    }
}
