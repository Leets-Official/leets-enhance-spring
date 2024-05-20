package leets.enhance.domain.Blade.status;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Level {
    LV0(0, 90, 5),
    LV1(1, 90, 5),
    LV2(2, 80, 5),
    LV3(3, 70, 5),
    LV4(4, 50, 10),
    LV5(5, 30, 15),
    LV6(6, 10, 20),
    LV7(7, 3, 25),
    LV8(8, 3, 30),
    LV9(9, 3, 35),
    LV10(10, 3, 40),
    LV11(11, 3, 45),
    LV12(12, 3, 50),
    LV13(13, 3, 50);

    private final Integer level;
    private final Integer successProbability;
    private final Integer breakProbability;
}
