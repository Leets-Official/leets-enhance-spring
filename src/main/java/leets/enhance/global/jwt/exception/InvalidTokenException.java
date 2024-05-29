package leets.attendance.global.jwt.exception;

import leets.attendance.global.error.dto.ErrorCode;
import leets.attendance.global.error.exception.ServiceException;

public class InvalidTokenException extends ServiceException {
    public InvalidTokenException() {
        super(ErrorCode.INVALID_TOKEN);
    }
}
