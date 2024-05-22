package leets.enhance.domain.Blade.usecase;

import leets.enhance.domain.Blade.domain.Blade;
import leets.enhance.domain.Blade.dto.response.EnhanceResponse;
import leets.enhance.domain.Blade.status.Level;

public interface EnhanceResultHandler {
    EnhanceResponse handle(Blade blade, Level level);
}
