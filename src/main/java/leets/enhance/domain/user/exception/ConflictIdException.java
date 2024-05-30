package leets.enhance.domain.user.exception;

import leets.enhance.global.error.ErrorCode;
import leets.enhance.global.error.ServiceException;


public class ConflictIdException extends ServiceException {
    public ConflictIdException() {
        super(ErrorCode.ID_CONFLICT.getHttpStatus(),ErrorCode.ID_CONFLICT.getMessage());
    }
}
