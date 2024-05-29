package leets.enhance.domain.user.DTO;

import lombok.*;

public class UserDto {
    @Getter
    @Setter
    @ToString
    @NoArgsConstructor
    public static class SignInDto {
        private String username;
        private String password;
    }
}
