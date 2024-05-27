package leets.enhance.domain.weapon.dto;

import leets.enhance.domain.user.domain.User;
import leets.enhance.domain.weapon.domain.Weapon;

public record GetItemsResponse(
        String weaponName,
        String level,
        Integer increaseProbability
) {
    public static GetItemsResponse from(User user, Weapon weapon) {
        return new GetItemsResponse(
                weapon.getWeaponName(),
                "lv."+ weapon.getLevel().toString(),
                user.getIncreasingProbability()
        );
    }
}
