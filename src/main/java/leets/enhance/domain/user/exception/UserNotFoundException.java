package leets.enhance.domain.user.exception;

import leets.enhance.global.error.ErrorCode;
import leets.enhance.global.error.ServiceException;

public class UserNotFoundException extends ServiceException {
        public UserNotFoundException() {
            super(ErrorCode.USER_NOT_FOUND.getHttpStatus(),ErrorCode.USER_NOT_FOUND.getMessage());
        }

}
