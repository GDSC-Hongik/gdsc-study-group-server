package com.gdgoc.study_group.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorCode {
  // 공통 에러
  INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "내부 서버 에러가 발생했습니다."),

  // Study & Round 관련 에러
  STUDY_NOT_FOUND(HttpStatus.NOT_FOUND, "존재하지 않는 스터디입니다."),
  ROUND_NOT_FOUND(HttpStatus.NOT_FOUND, "존재하지 않는 회차입니다."),
  UNAUTHORIZED_ROUND_ACCESS(HttpStatus.FORBIDDEN, "해당 회차에 대한 권한이 없습니다."),
  NOT_STUDY_LEADER(HttpStatus.FORBIDDEN, "스터디 리더만 이 작업을 수행할 수 있습니다."),
  INVALID_ROUND_DATE(HttpStatus.BAD_REQUEST, "회차 날짜가 유효하지 않습니다."),
  INVALID_ROUND_STATUS(HttpStatus.BAD_REQUEST, "유효하지 않은 회차 상태입니다.");

  private final HttpStatus status;
  private final String message;
}
