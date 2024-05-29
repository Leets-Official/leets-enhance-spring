package leets.attendance.global.error.exception;

import leets.attendance.global.error.dto.ErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ServiceException extends RuntimeException {
    private final ErrorCode errorCode;
}