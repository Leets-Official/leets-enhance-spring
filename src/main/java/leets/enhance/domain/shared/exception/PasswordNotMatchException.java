package leets.enhance.domain.shared.exception;

import leets.enhance.global.error.ErrorCode;
import leets.enhance.global.error.exception.ServiceException;

public class PasswordNotMatchException extends ServiceException {
    public PasswordNotMatchException() {
        super(ErrorCode.PASSWORD_NOT_MATCH);
    }
}
