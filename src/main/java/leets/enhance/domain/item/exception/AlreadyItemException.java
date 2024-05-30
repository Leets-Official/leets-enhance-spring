package leets.enhance.domain.item.exception;


import leets.enhance.global.error.dto.ErrorCode;
import leets.enhance.global.error.exception.ServiceException;

public class AlreadyItemException extends ServiceException {
    public AlreadyItemException(){
        super(ErrorCode.ALREADY_ITEM_EXCEPTION);
    }
}
