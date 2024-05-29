package leets.enhance.gloal.error;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCode {
    NO_COUPON(400, "NO_COUPON", "쿠폰이 존재하지 않습니다."),
    TOO_LONG_BLADE_NAME(400, "TOO_LONG_BLADE_NAME", "이름은 5자 이하로 입력해주세요."),
    USER_NOT_FOUND(404, "USER_NOT_FOUND", "존재하지 않는 사용자입니다.");

    private final int httpStatus;
    private final String code;
    private final String message;
}