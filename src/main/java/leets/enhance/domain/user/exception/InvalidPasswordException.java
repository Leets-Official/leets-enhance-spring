package leets.enhance.domain.user.exception;

import leets.enhance.global.error.ErrorCode;
import leets.enhance.global.error.ServiceException;

public class InvalidPasswordException extends ServiceException {
    public InvalidPasswordException() {
        super(ErrorCode.INVALID_PASSWORD.getHttpStatus(),ErrorCode.INVALID_PASSWORD.getMessage());
    }
}
