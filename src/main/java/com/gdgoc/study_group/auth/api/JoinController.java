package com.gdgoc.study_group.auth.api;

import com.gdgoc.study_group.auth.application.JoinService;
import com.gdgoc.study_group.auth.dto.JoinDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Tag(name = "Auth", description = "로그인 API")
public class JoinController {

    private final JoinService joinService;

    /**
     * 학번과 비밀번호 등의 정보로 회원 가입을 합니다.
     *
     * @param request 회원 가입에 필요한 데이터 dto
     * @return 생성 시, 201 회원 가입 성공 메시지
     */
    @Operation(summary = "회원 가입", description = "학번과 비밀번호 등 정보 입력 시 회원 가입")
    @PostMapping("/join")
    public ResponseEntity<Void> joinMember(@RequestBody JoinDto request) {

        joinService.joinMember(request);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
