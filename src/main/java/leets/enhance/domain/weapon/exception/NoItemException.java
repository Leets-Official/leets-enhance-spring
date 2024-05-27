package leets.enhance.domain.weapon.exception;


import leets.enhance.global.error.ErrorCode;
import leets.enhance.global.error.ServiceException;

public class NoItemException extends ServiceException {
    public NoItemException() {
        super(ErrorCode.NO_ITEM.getHttpStatus(),ErrorCode.NO_ITEM.getMessage());
    }
}
