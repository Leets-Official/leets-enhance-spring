package leets.enhance.global.jwt;

import leets.enhance.global.error.ErrorCode;
import leets.enhance.global.error.ServiceException;

public class InvalidTokenException extends ServiceException {
    public InvalidTokenException() {
        super(ErrorCode.INVALID_TOKEN.getHttpStatus());
    }
}
