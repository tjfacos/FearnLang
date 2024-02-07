package FearnStdLib;

import java.util.Random;

public class RandomNumbers {
    public static Double random() { return Math.random(); }
    public static Integer randomInRange(Integer LB, Integer UB) 
        { return LB + new Random().nextInt(UB-LB);}
}
