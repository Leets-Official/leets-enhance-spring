package leets.attendance.domain.user.presentation.dto.Request;

import jakarta.validation.constraints.NotNull;
import leets.attendance.domain.user.domain.Part;

public record RegisterRequestDto(
        @NotNull
        String id,

        @NotNull
        String name,

        @NotNull
        String password,

        @NotNull
        Part part
) {
}
