package com.thegongoliers.math;

import com.thegongoliers.math.spline.HermiteSpline;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by Kyle on 3/5/2017.
 */
public class HermiteSplineTest {
    @Test
    public void calculate() {
        HermiteSpline hermiteSpline = new HermiteSpline(0, 0, 2, 0);
        assertEquals(hermiteSpline.calculate(0), 0, 0);
        assertEquals(hermiteSpline.calculate(1), 2, 0);
        assertEquals(hermiteSpline.calculate(0.5), 1, 0.0000001);
        assertEquals(hermiteSpline.calculate(0.75), 1.6875, 0.0001);
    }

    @Test
    public void calculateTime() {
        HermiteSpline hermiteSpline = new HermiteSpline(0, 0, 2, 0, 4);
        assertEquals(hermiteSpline.calculate(0), 0, 0);
        assertEquals(hermiteSpline.calculate(2), 1, 0);
        assertEquals(hermiteSpline.calculate(3), 1.6875, 0.0001);
        assertEquals(hermiteSpline.calculate(4), 2, 0);
    }

}