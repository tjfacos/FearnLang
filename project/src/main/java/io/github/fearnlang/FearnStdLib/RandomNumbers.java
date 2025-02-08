package io.github.fearnlang.FearnStdLib;

import java.util.Random;

public class RandomNumbers {

    /**
     * Returns a random double between 0 and 1
     * @return A random double between 0 and 1
     */
    public static Double random() {
        return Math.random();
    }

    /**
     * Returns a random double between LB and UB
     * @param LB The lower bound
     * @param UB The upper bound
     * @return
     */
    public static Integer randomInRange(Integer LB, Integer UB) {
        return LB + new Random().nextInt(UB - LB);
    }
}
