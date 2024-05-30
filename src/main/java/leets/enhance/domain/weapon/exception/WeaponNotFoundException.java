package leets.enhance.domain.weapon.exception;

import leets.enhance.global.error.ErrorCode;
import leets.enhance.global.error.ServiceException;

public class WeaponNotFoundException extends ServiceException {
        public WeaponNotFoundException() {
            super(ErrorCode.WEAPON_NOT_FOUND.getHttpStatus(),ErrorCode.WEAPON_NOT_FOUND.getMessage());
        }

}
