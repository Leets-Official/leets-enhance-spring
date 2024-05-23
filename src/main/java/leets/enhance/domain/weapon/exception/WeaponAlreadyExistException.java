package leets.enhance.domain.weapon.exception;

import leets.enhance.global.error.ErrorCode;
import leets.enhance.global.error.ServiceException;

public class WeaponAlreadyExistException extends ServiceException {
        public WeaponAlreadyExistException() {
            super(ErrorCode.ALREADY_EXIST.getHttpStatus(),ErrorCode.ALREADY_EXIST.getMessage());
        }

}
