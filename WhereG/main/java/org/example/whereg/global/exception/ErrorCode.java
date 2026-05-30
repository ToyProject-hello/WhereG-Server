package org.example.whereg.global.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {

    // 공통
    INVALID_INPUT(HttpStatus.BAD_REQUEST, "400", "잘못된 입력값입니다."),
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "500", "서버 오류가 발생했습니다."),
    NOT_FOUND(HttpStatus.NOT_FOUND, "404", "요청한 리소스를 찾을 수 없습니다."),
    FORBIDDEN(HttpStatus.FORBIDDEN, "403", "접근 권한이 없습니다."),
    UNAUTHORIZED(HttpStatus.UNAUTHORIZED, "401", "인증이 필요합니다."),
    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "404", "존재하지 않는 유저입니다."),
    DUPLICATE_EMAIL(HttpStatus.CONFLICT, "409", "이미 사용 중인 이메일입니다."),
    PASSWORD_MISMATCH(HttpStatus.UNAUTHORIZED, "401", "비밀번호가 올바르지 않습니다."),
    INVALID_REFRESH_TOKEN(HttpStatus.UNAUTHORIZED, "401", "유효하지 않은 리프레시 토큰입니다."),
    EXPIRED_REFRESH_TOKEN(HttpStatus.UNAUTHORIZED, "401", "만료된 리프레시 토큰입니다."),
    EMAIL_CODE_EXPIRED(HttpStatus.UNAUTHORIZED, "401", "만료된 인증번호입니다."),
    EMAIL_CODE_MISMATCH(HttpStatus.UNAUTHORIZED, "400", "잘못된 인증번호입니다."),

    // 필요에 따라 도메인별로 추가
    // ex) USER-001, POST-001 ...
    ;

    private final HttpStatus httpStatus;
    private final String code;
    private final String message;
}
