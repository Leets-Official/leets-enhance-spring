package leets.enhance.domain.weapon.dto;

import leets.enhance.domain.weapon.domain.Weapon;

import java.util.List;

public record GetItemsTopResponse(String weapons) {
    public static GetItemsTopResponse of(Weapon weapon) {
        return new GetItemsTopResponse(
                 weapon.getWeaponName()+"lv."+ weapon.getLevel().toString()
        );
    }
}
