package leets.enhance.domain.blade.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

@Getter
@AllArgsConstructor
public enum BladeLevel {
    LV0(0, 1.0, 0.0),
    LV1(1, 0.9, 0.05),
    LV2(2, 0.8, 0.05),
    LV3(3, 0.7, 0.05),
    LV4(4, 0.5, 0.1),
    LV5(5, 0.3, 0.15),
    LV6(6, 0.1, 0.2),
    LV7(7, 0.03, 0.25),
    LV8(8, 0.03, 0.3),
    LV9(9, 0.03, 0.35),
    LV10(10, 0.03, 0.4),
    LV11(11, 0.03, 0.45),
    LV12(12, 0.03, 0.5);

    private final Integer level;
    private final Double successProbability;
    private final Double DestroyProbability;

    public static BladeLevel fromLevel(Integer level) {
        return Arrays.stream(BladeLevel.values())
                .filter(l -> l.getLevel().equals(level))
                .findFirst()
                .orElse(BladeLevel.LV12);
    }
}