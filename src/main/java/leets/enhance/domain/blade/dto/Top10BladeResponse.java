package leets.enhance.domain.blade.dto;

import leets.enhance.domain.blade.domain.Blade;

public record Top10BladeResponse(
        String name,
        int level,
        String username
) {
    public static Top10BladeResponse of(Blade blade) {
        return new Top10BladeResponse(blade.getName(), blade.getLevel().getLevel(), blade.getUser().getName());
    }
}