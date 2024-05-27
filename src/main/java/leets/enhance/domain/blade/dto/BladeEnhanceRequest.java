package leets.enhance.domain.blade.dto;

import jakarta.validation.constraints.NotBlank;

public record BladeEnhanceRequest(
        @NotBlank
        Boolean UseCoupon) {
}