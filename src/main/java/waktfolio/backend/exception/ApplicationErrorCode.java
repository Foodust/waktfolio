package waktfolio.backend.exception;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;


@Getter
@AllArgsConstructor
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum ApplicationErrorCode {

  NOT_FOUND_MEMBER(400, "존재 하지 않는 멤버 입니다."),
  NOT_FOUND_PROJECT(400, "존재 하지 않는 프로젝트 입니다"),
  NOT_FOUND_BUSINESS_REQUEST(400, "존재 하지 않는 업무 요청 입니다."),
  NOT_FOUND_BUSINESS_REQUEST_ANSWER(400, "존재 하지 않는 업무 요청 답변 입니다."),
  NOT_FOUND_PERSONAL(400, "존재 하지 않는 멤버 설정 입니다."),
  NOT_FOUND_MAN_MONTH_INFO(400, "존재 하지 않는 멤버 공수 입니다."),
  NOT_MATCHED_PASSWORD(400, "비밀 번호가 일치 하지 않습니다."),
  NOT_COMMON_CODE(400, "공통코드를 찾을 수 없습니다."),
  DUPLICATE_COMMON_CODE_NAME(400, "이미 존재하는 이름입니다."),
  ALREADY_EXIST_USER_ID(400, "이미 존재 하는 아이디 입니다."),

  DOES_NOT_HAVE_PERMISSION(402,"권한이 없습니다."),
  DOES_NOT_MATCH_PASSWORD(400,"패스워드 규칙이 맞지 않습니다."),
  EXPIRED_JWT_TOKEN(401, "로그인 시간이 만료되었습니다."),
  INVALID_TOKEN(400, "잘못된 토큰입니다."),;

  private final int status;
  private final String message;

}