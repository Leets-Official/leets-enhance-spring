package leets.enhance.domain.user.dto;

import jakarta.validation.constraints.NotBlank;

public record DuplicateUserIdRequest(
        @NotBlank
        String email
) {
}