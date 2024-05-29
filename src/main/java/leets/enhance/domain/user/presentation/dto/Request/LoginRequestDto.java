package leets.attendance.domain.user.presentation.dto.Request;

import jakarta.validation.constraints.NotNull;

public record LoginRequestDto(
        @NotNull
        String id,
        @NotNull
        String password
) {
}
