package leets.enhance.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorCode {
    // User
    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "유저를 찾을 수 없습니다."),
    DUPLICATED_EMAIL(HttpStatus.BAD_REQUEST, "이미 존재하는 이메일입니다."),
    INVALID_PASSWORD(HttpStatus.BAD_REQUEST, "비밀번호가 일치하지 않습니다."),

    // Item
    ITEM_NOT_FOUND(HttpStatus.NOT_FOUND, "아이템을 찾을 수 없습니다."),
    ITEM_ALREADY_EXISTS(HttpStatus.BAD_REQUEST, "이미 아이템을 소유하고 있습니다."),
    NOT_ENOUGH_BOOSTER(HttpStatus.BAD_REQUEST, "확률 증가권이 부족합니다.");

    private final HttpStatus httpStatus;
    private final String message;
}
