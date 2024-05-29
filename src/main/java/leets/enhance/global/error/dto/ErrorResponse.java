package leets.attendance.global.error.dto;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class ErrorResponse {
    private final HttpStatus status;
    private final String error;
    private final String code;
    private final String message;

    public ErrorResponse(ErrorCode errorCode) {
        this.status = errorCode.getHttpStatus();
        this.error = errorCode.getHttpStatus().name();
        this.code = errorCode.getCode();
        this.message = errorCode.getMessage();
    }
    public static ErrorResponse of(ErrorCode errorCode) {
        return new ErrorResponse(errorCode);
    }
}
