package leets.enhance.domain.user.exception;


import leets.enhance.global.error.dto.ErrorCode;
import leets.enhance.global.error.exception.ServiceException;

public class InvalidPasswordException extends ServiceException {
    public InvalidPasswordException(){
        super(ErrorCode.INVALID_PASSWORD_EXCEPTION);
    }
}
