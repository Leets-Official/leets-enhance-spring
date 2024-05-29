package leets.enhance.domain.user.exception;

import leets.enhance.gloal.error.ErrorCode;
import leets.enhance.gloal.error.exception.ServiceException;

public class UserNotFoundException extends ServiceException {
    public UserNotFoundException() {
        super(ErrorCode.USER_NOT_FOUND);
    }
}