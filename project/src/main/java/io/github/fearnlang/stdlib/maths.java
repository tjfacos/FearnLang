package io.github.fearnlang.stdlib;

public class maths {

    /**
     * Returns the value of PI
     * @return
     */
    public static Double PI() {
        return Math.PI;
    }

    /**
     * Returns the value of Euler's number
     * @return
     */
    public static Double Eulers() {
        return Math.exp(1.0);
    }

    /**
     * Returns the sine of theta
     * @param theta The angle in radians
     * @return
     */
    public static Double sin(Double theta) {
        return Math.sin(theta);
    }

    /**
     * Returns the cosine of theta
     * @param theta The angle in radians
     * @return
     */
    public static Double cos(Double theta) {
        return Math.cos(theta);
    }

    /**
     * Returns the tangent of theta
     * @param theta The angle in radians
     * @return
     */
    public static Double tan(Double theta) {
        return Math.tan(theta);
    }
}
