package leets.enhance.domain.user.presentation.dto.Response;

import jakarta.validation.constraints.NotNull;

public record TokenResponseDto(
        @NotNull
        String accessToken

) {
}
