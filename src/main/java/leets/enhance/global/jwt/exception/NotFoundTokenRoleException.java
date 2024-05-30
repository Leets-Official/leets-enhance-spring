package leets.enhance.global.jwt.exception;


import leets.enhance.global.error.dto.ErrorCode;
import leets.enhance.global.error.exception.ServiceException;

public class NotFoundTokenRoleException extends ServiceException {
    public NotFoundTokenRoleException(){
        super(ErrorCode.NOT_FOUND_TOKEN_ROLL);
    }
}
