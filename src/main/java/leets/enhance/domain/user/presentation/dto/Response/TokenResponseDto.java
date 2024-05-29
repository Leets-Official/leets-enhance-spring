package leets.attendance.domain.user.presentation.dto.Response;

import jakarta.validation.constraints.NotNull;

public record TokenResponseDto(
        @NotNull
        String accessToken,

        @NotNull
        String refreshToken
) {
}
