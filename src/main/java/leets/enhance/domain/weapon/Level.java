package leets.enhance.domain.weapon;

import java.util.Arrays;
import java.util.Optional;

public enum Level {
        LV0(0, 100, 0),
        LV1(1, 90, 5),
        LV2(2, 80, 5),
        LV3(3, 70, 5),
        LV4(4, 50, 5),
        LV5(5, 30, 10),
        LV6(6, 10, 15),
        LV7(7, 3, 20);

        private final int level;
        private final int successRate;
        private final int destroyRate;

        Level(int level, int successRate, int destroyRate) {
            this.level = level;
            this.successRate = successRate;
            this.destroyRate = destroyRate;
        }
        public int getLevel() {
                return level;
        }

        public int getSuccessRate() {
                return successRate;
        }

        public int getDestroyRate() {
                return destroyRate;
        }

        public static Optional<Level> getLevelByNumber(int levelNumber) {
                return Arrays.stream(values())
                        .filter(level -> level.getLevel() == levelNumber)
                        .findFirst();
        }
}
