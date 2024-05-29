package leets.attendance.domain.user.exception;

import leets.attendance.global.error.dto.ErrorCode;
import leets.attendance.global.error.exception.ServiceException;

public class InvalidPasswordException extends ServiceException {
    public InvalidPasswordException(){
        super(ErrorCode.INVALID_PASSWORD_EXCEPTION);
    }
}
