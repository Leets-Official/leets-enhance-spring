package leets.enhance.domain.blade.exception;

import leets.enhance.gloal.error.ErrorCode;
import leets.enhance.gloal.error.exception.ServiceException;

public class TooLongBladeNameException extends ServiceException {
    public TooLongBladeNameException() {
        super(ErrorCode.TOO_LONG_BLADE_NAME);
    }
}
