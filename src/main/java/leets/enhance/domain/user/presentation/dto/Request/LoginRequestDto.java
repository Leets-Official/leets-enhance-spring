package leets.enhance.domain.user.presentation.dto.Request;

import jakarta.validation.constraints.NotNull;

public record LoginRequestDto(
        @NotNull
        String email,
        @NotNull
        String password
) {
}
