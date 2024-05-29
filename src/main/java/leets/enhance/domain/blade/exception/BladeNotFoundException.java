package leets.enhance.domain.blade.exception;

import leets.enhance.gloal.error.ErrorCode;
import leets.enhance.gloal.error.exception.ServiceException;

public class BladeNotFoundException extends ServiceException {
    public BladeNotFoundException() {
        super(ErrorCode.USER_NOT_FOUND);
    }
}