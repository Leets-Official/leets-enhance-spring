package leets.enhance.domain.Blade.response;

import leets.enhance.domain.Blade.domain.Blade;

public record SingleItemResponse(String bladeName,
                                 Integer level,
                                 String username) {

    public static SingleItemResponse of(Blade blade) {
        return new SingleItemResponse(
                blade.getName(),
                blade.getLevel(),
                blade.getUser().getUsername()
        );
    }
}
