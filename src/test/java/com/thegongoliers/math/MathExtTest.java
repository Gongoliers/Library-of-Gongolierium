package com.thegongoliers.math;

import org.junit.Test;

import static org.junit.Assert.*;

public class MathExtTest {

    @Test
    public void testMean(){
        assertEquals(0, MathExt.mean(new double[]{0}), 0.0);
        assertEquals(1, MathExt.mean(new double[]{0, 2}), 0.0);
        assertEquals(4, MathExt.mean(new double[]{0, 2, 4, 6, 8}), 0.0);
        assertEquals(0, MathExt.mean(new double[]{}), 0.0);
        assertEquals(0, MathExt.mean(null), 0.0);
    }

    @Test
    public void testStandardDeviation(){
        assertEquals(0, MathExt.standardDeviation(new double[]{0}), 0.0);
        assertEquals(0, MathExt.standardDeviation(new double[]{1}), 0.0);
        assertEquals(Math.sqrt(2), MathExt.standardDeviation(new double[]{0, 2}), 0.001);
        assertEquals(164.7118, MathExt.standardDeviation(new double[]{600, 470, 170, 430, 300}), 0.001);
        assertEquals(0, MathExt.standardDeviation(new double[]{}), 0);
        assertEquals(0, MathExt.standardDeviation(null), 0);
    }

    @Test
    public void testCorrelation(){
        assertEquals(0.9575, MathExt.correlation(new double[]{14.2, 16.4, 11.9, 15.2, 18.5, 22.1, 19.4, 25.1, 23.4, 18.1, 22.6, 17.2},
                new double[]{215, 325, 185, 332, 406, 522, 412, 614, 544, 421, 445, 408}), 0.00005);
    }

    @Test(expected = MathExt.UnequalLengthException.class)
    public void testCorrelationException(){
        MathExt.correlation(new double[]{}, new double[]{1});
    }

}