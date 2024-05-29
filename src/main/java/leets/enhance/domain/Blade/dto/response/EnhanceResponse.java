package leets.enhance.domain.Blade.dto.response;

import leets.enhance.domain.Blade.status.Level;

public record EnhanceResponse(Boolean enhanceResult,
                              Boolean breakResult,
                              Level level) {
}