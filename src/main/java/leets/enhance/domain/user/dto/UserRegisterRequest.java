package leets.enhance.domain.user.dto;

import jakarta.validation.constraints.NotBlank;
import leets.enhance.domain.user.domain.User;
import lombok.Builder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Collections;

public record UserRegisterRequest(
        @NotBlank
        String email,
        @NotBlank
        String password,
        @NotBlank
        String name

) {
    @Builder
    public User toUser(PasswordEncoder passwordEncoder) {
        return User.builder()
                .username(email)
                .password(passwordEncoder.encode(password))
                .upgradeCouponRemaining(3)
                .name(name)
                .roles(Collections.singletonList("USER"))
                .build();
    }
}
