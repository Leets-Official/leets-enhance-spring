package leets.enhance.global.jwt;

import leets.enhance.global.error.ErrorCode;
import leets.enhance.global.error.ServiceException;

public class ExpiredTokenException extends ServiceException {
    public ExpiredTokenException() {
        super(ErrorCode.EXPIRED_TOKEN.getHttpStatus());
    }
}
