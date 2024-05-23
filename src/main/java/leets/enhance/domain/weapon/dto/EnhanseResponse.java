package leets.enhance.domain.weapon.dto;

import leets.enhance.domain.weapon.WeaponResponseMessage;

public record EnhanseResponse(
        WeaponResponseMessage weaponResponseMessage,
        Integer level
) {
}
