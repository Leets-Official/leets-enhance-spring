package leets.enhance.domain.user.exception;

import leets.enhance.global.error.ErrorCode;
import leets.enhance.global.error.ServiceException;

public class InvalidIdException extends ServiceException {
    public InvalidIdException() {
        super(ErrorCode.INVALID_ID.getHttpStatus(),ErrorCode.INVALID_ID.getMessage());
    }
}
