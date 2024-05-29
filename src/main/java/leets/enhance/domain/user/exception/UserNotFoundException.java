package leets.attendance.domain.user.exception;

import leets.attendance.global.error.dto.ErrorCode;
import leets.attendance.global.error.exception.ServiceException;

public class UserNotFoundException extends ServiceException {
    public UserNotFoundException(){
        super(ErrorCode.USER_NOT_FOUND_EXCEPTION);
    }
}
