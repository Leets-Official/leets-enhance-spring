package leets.enhance.domain.Blade.exception;

import leets.enhance.global.error.ErrorCode;
import leets.enhance.global.error.exception.ServiceException;

public class LevelNotFoundException extends ServiceException {
    public LevelNotFoundException() {
        super(ErrorCode.USER_NOT_FOUND);
    }
}
