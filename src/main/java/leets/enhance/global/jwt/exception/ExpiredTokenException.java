package leets.attendance.global.jwt.exception;

import leets.attendance.global.error.dto.ErrorCode;
import leets.attendance.global.error.exception.ServiceException;

public class ExpiredTokenException extends ServiceException {
    public ExpiredTokenException() {
        super(ErrorCode.EXPIRED_TOKEN);
    }
}
