package io.github.fearnlang.stdlib;

import java.util.Random;

public class random {

    /**
     * Returns a random double between 0 and 1
     * @return A random double between 0 and 1
     */
    public static Double rand() {
        return Math.random();
    }

    /**
     * Returns a random double between LB and UB
     * @param LB The lower bound
     * @param UB The upper bound
     * @return
     */
    public static Integer randInRange(Integer LB, Integer UB) {
        return LB + new Random().nextInt(UB - LB);
    }
}
