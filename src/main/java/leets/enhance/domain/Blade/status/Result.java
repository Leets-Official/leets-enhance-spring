package leets.enhance.domain.Blade.status;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Result {
    SUCCESS("강화 성공"),
    FAIL("강화 실패"),
    BREAK("무기 파괴"),
    DOWNGRADE("등급 하락");

    private final String resultKo;
}