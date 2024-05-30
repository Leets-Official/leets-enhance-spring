package leets.enhance.domain.blade.dto;

import leets.enhance.domain.blade.domain.Blade;
import leets.enhance.domain.user.domain.User;

public record BladeCreateResponse(
        String name,
        Integer level,
        String userName
) {
   public static BladeCreateResponse of(Blade blade, User user) {
       return new BladeCreateResponse(blade.getName(), blade.getLevel().getLevel(), user.getName());
    }
}
