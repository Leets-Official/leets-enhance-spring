package leets.enhance.domain.user.exception;


import leets.enhance.global.error.dto.ErrorCode;
import leets.enhance.global.error.exception.ServiceException;

public class UserNotFoundException extends ServiceException {
    public UserNotFoundException(){
        super(ErrorCode.USER_NOT_FOUND_EXCEPTION);
    }
}
