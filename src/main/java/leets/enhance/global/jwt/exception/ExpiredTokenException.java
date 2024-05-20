package leets.enhance.global.jwt.exception;


import leets.enhance.global.error.ErrorCode;
import leets.enhance.global.error.exception.ServiceException;

public class ExpiredTokenException extends ServiceException {
    public ExpiredTokenException() {
        super(ErrorCode.EXPIRED_TOKEN);
    }
}
