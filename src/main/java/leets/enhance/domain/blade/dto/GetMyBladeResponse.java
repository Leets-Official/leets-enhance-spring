package leets.enhance.domain.blade.dto;

import leets.enhance.domain.blade.domain.Blade;
import leets.enhance.domain.user.domain.User;

public record GetMyBladeResponse(
        String name,
        Integer level,
        String userName
) {
    public static GetMyBladeResponse of(Blade blade, User user) {
        return new GetMyBladeResponse(blade.getName(), blade.getLevel().getLevel(), user.getName());
    }
}
