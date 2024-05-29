package leets.enhance.domain.user.exception;

import leets.enhance.gloal.error.ErrorCode;
import leets.enhance.gloal.error.exception.ServiceException;

public class NoCouponException extends ServiceException {
    public NoCouponException() {
        super(ErrorCode.NO_COUPON);
    }
}
