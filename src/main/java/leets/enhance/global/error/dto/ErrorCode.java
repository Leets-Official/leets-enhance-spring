package leets.attendance.global.error.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorCode {
    BAD_REQUEST(HttpStatus.BAD_REQUEST, "BAD_REQUEST", "잘못된 요청입니다."),
    NOT_FOUND(HttpStatus.NOT_FOUND, "NOT_FOUND", "리소스를 찾을 수 없습니다."),
    METHOD_NOT_ALLOWED(HttpStatus.METHOD_NOT_ALLOWED, "METHOD_NOT_ALLOWED", "허용되지 않은 HTTP 메서드 입니다."),
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "INTERNAL_SERVER_ERROR", "내부에서 서버 오류가 발생했습니다."),
    INVALID_TOKEN(HttpStatus.UNAUTHORIZED, "UNAUTHORIZED","유효하지 않은 토큰입니다."),
    EXPIRED_TOKEN(HttpStatus.UNAUTHORIZED, "UNAUTHORIZED", "만료된 토큰입니다."),
    NOT_FOUND_TOKEN_ROLL(HttpStatus.FORBIDDEN, "FORBIDDEN", "토큰의 ROLE을 확인할 수 없습니다."),
    USER_NOT_FOUND_EXCEPTION(HttpStatus.NOT_FOUND,"USER_NOT_FOUND_EXCEPTION","사용자를 찾을 수 없습니다."),
    INVALID_PASSWORD_EXCEPTION(HttpStatus.UNAUTHORIZED,"INVALID_PASSWORD_EXCEPTION","비밀번호가 일치하지 않습니다.");

    private final HttpStatus httpStatus;
    private final String code;
    private final String message;
}
