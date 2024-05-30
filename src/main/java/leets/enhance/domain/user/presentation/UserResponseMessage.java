package leets.enhance.domain.user.presentation;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum UserResponseMessage {
    SUCCESS_LOGIN("로그인에 성공했습니다."),
    SUCCESS_REGISTER("회원가입에 성공했습니다."),
    USABLE_ID("사용 가능한 아이디입니다.");
    private String message;
}
