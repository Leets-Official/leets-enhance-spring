package leets.enhance.domain.blade.dto;

import jakarta.validation.constraints.NotBlank;

public record BladeCreateRequest(
        @NotBlank
        String name
) {
}
