package com.gdgoc.study_group.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorCode {
  // 공통 에러
  INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "내부 서버 에러가 발생했습니다."),
  INVALID_REQUEST(HttpStatus.BAD_REQUEST, "잘못된 요청입니다."),

  // Study 관련 에러
  STUDY_NOT_FOUND(HttpStatus.NOT_FOUND, "존재하지 않는 스터디입니다."),
  STUDY_MEMBER_NOT_FOUND(HttpStatus.NOT_FOUND, "스터디 멤버를 찾을 수 없습니다."),
  NOT_STUDY_MEMBER(HttpStatus.FORBIDDEN, "해당 스터디의 멤버가 아닙니다."),

  // Round 관련 에러
  ROUND_NOT_FOUND(HttpStatus.NOT_FOUND, "존재하지 않는 회차입니다."),
  NOT_STUDY_LEADER(HttpStatus.FORBIDDEN, "스터디 리더만 이 작업을 수행할 수 있습니다."),
  UNAUTHORIZED_ROUND_ACCESS(HttpStatus.FORBIDDEN, "해당 회차에 대한 권한이 없습니다."),
  INVALID_ROUND_DATE(HttpStatus.BAD_REQUEST, "회차 날짜가 유효하지 않습니다."),
  INVALID_ROUND_STATUS(HttpStatus.BAD_REQUEST, "유효하지 않은 회차 상태입니다.");

  private final HttpStatus status;
  private final String message;
}
