package com.gdgoc.study_group.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorCode {
  INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "내부 서버 에러가 발생했습니다."),
  FORBIDDEN(HttpStatus.FORBIDDEN, "접근 권한이 없습니다."),

  // member
  MEMBER_NOT_FOUND(HttpStatus.NOT_FOUND, "존재하지 않는 멤버입니다."),

  // study
  STUDY_NOT_FOUND(HttpStatus.NOT_FOUND, "존재하지 않는 스터디입니다."),

  // round
  ROUND_NOT_FOUND(HttpStatus.NOT_FOUND, "존재하지 않는 회차입니다."),

  // round member
  RETROSPECT_NOT_FOUND(HttpStatus.NOT_FOUND, "존재하지 않는 회고입니다."),
  RETROSPECT_ALREADY_EXIST(HttpStatus.CONFLICT, "이미 회고를 작성했습니다."),

  // comment
  COMMENT_NOT_FOUND(HttpStatus.NOT_FOUND, "존재하지 않는 댓글입니다."),

  ;
  private final HttpStatus status;
  private final String message;
}
