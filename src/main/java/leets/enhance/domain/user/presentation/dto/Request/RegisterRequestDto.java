package leets.enhance.domain.user.presentation.dto.Request;

import jakarta.validation.constraints.NotNull;

public record RegisterRequestDto(
        @NotNull
        String email,

        @NotNull
        String name,

        @NotNull
        String password

) {
}
