package leets.enhance.domain.user.dto;

import jakarta.validation.constraints.NotBlank;

public record UserLoginRequest(
        @NotBlank
        String email,
        @NotBlank
        String password
) {
}
