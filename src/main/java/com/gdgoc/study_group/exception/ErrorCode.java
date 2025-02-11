package com.gdgoc.study_group.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorCode {
  INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "내부 서버 에러가 발생했습니다."),

  // study
  STUDY_NOT_FOUND(HttpStatus.NOT_FOUND, "존재하지 않는 스터디입니다."),

  // apply
  APPLY_NO_QUESTION(HttpStatus.BAD_REQUEST, "질문이 존재하지 않으나, 답변이 존재합니다."),
  APPLY_TOO_MANY(HttpStatus.BAD_REQUEST, "스터디당 지원은 하나만 등록가능합니다."),
  APPLY_NO_MEMBER(HttpStatus.BAD_REQUEST, "해당하는 지원 기록이 없습니다."),

  // member
  MEMBER_NOT_FOUND(HttpStatus.NOT_FOUND, "존재하지 않는 회원입니다.")
  ;

  private final HttpStatus status;
  private final String message;
}
