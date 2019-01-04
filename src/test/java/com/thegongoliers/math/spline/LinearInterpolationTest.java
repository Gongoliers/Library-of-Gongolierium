package com.thegongoliers.math.spline;

import com.thegongoliers.math.spline.LinearInterpolation;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by Kyle on 3/5/2017.
 */
public class LinearInterpolationTest {
    @Test
    public void calculate() {
        LinearInterpolation linearInterpolation = new LinearInterpolation(0, 2);
        assertEquals(linearInterpolation.calculate(0.5), 1, 0);
        assertEquals(linearInterpolation.calculate(1), 2, 0);
        assertEquals(linearInterpolation.calculate(0), 0, 0);
        assertEquals(linearInterpolation.calculate(0.75), 1.5, 0);
        LinearInterpolation lerp = new LinearInterpolation(2, -2);
        assertEquals(lerp.calculate(0.5), 0, 0);
        assertEquals(lerp.calculate(0.25), 1, 0);
        assertEquals(lerp.calculate(0.75), -1, 0);
        assertEquals(lerp.derivative(0.5), -4, 0);
        assertEquals(lerp.doubleDerivative(0.5), 0, 0);
        assertEquals(lerp.integral(1), 0, 0);
        assertEquals(linearInterpolation.integral(1), 1, 0);
    }

    @Test
    public void calculateTime() {
        LinearInterpolation linearInterpolation = new LinearInterpolation(0, 2, 4);
        assertEquals(linearInterpolation.calculate(2), 1, 0);
        assertEquals(linearInterpolation.calculate(4), 2, 0);
        assertEquals(linearInterpolation.calculate(0), 0, 0);
        assertEquals(linearInterpolation.calculate(3), 1.5, 0);
    }

}