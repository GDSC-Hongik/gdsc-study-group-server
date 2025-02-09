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
  STUDY_QUESTION_NOT_FOUND(HttpStatus.NOT_FOUND, "지원 질문이 존재하지 않습니다."),

  // member
  MEMBER_NOT_FOUND(HttpStatus.NOT_FOUND, "존재하지 않는 회원입니다.")
  ;

  private final HttpStatus status;
  private final String message;
}
