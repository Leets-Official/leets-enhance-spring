package leets.enhance.domain.weapon;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum WeaponResponseMessage {
    SUCCESS_CREATE("생성에 성공했습니다."),
    SUCCESS_UPDATE("변경에 성공했습니다."),
    UPGRADE_SUCCESS("강화 성공!"),
    UPGRADE_FAILED("강화 실패"),
    LEVEL_DOWN("레벨 하락"),
    DESTROYED("장비 파괴");
    private String message;
}
