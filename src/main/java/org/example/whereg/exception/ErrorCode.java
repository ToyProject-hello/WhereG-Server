package org.example.whereg.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {

    // 공통
    INVALID_INPUT(HttpStatus.BAD_REQUEST, "COMMON-001", "잘못된 입력값입니다."),
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "COMMON-002", "서버 오류가 발생했습니다."),
    NOT_FOUND(HttpStatus.NOT_FOUND, "COMMON-003", "요청한 리소스를 찾을 수 없습니다."),

    // 필요에 따라 도메인별로 추가
    // ex) USER-001, POST-001 ...
    ;

    private final HttpStatus httpStatus;
    private final String code;
    private final String message;
}
