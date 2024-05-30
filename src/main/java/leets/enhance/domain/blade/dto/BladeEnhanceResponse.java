package leets.enhance.domain.blade.dto;

import leets.enhance.domain.blade.domain.Blade;

public record BladeEnhanceResponse(
        String name,
        Integer level,
        String result
) {
    public static BladeEnhanceResponse of(Blade blade, String result) {
        return new BladeEnhanceResponse(blade.getName(), blade.getLevel().getLevel(), result);
    }
}
