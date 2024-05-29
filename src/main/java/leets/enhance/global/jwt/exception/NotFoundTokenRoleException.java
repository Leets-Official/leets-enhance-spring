package leets.attendance.global.jwt.exception;

import leets.attendance.global.error.dto.ErrorCode;
import leets.attendance.global.error.exception.ServiceException;

public class NotFoundTokenRoleException extends ServiceException {
    public NotFoundTokenRoleException(){
        super(ErrorCode.NOT_FOUND_TOKEN_ROLL);
    }
}
