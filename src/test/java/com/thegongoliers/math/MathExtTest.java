package com.thegongoliers.math;

import com.thegongoliers.math.exceptions.OutOfBoundsException;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.*;

public class MathExtTest {

    @Test
    public void testIsOdd(){
        assertTrue(MathExt.isOdd(1));
        assertTrue(MathExt.isOdd(3));
        assertTrue(MathExt.isOdd(-1));

        assertFalse(MathExt.isOdd(0));
        assertFalse(MathExt.isOdd(2));
        assertFalse(MathExt.isOdd(-2));
    }

    @Test
    public void testIsEven(){
        assertTrue(MathExt.isEven(0));
        assertTrue(MathExt.isEven(2));
        assertTrue(MathExt.isEven(-2));

        assertFalse(MathExt.isEven(1));
        assertFalse(MathExt.isEven(3));
        assertFalse(MathExt.isEven(-1));
    }

    @Test
    public void testMagnitude(){
        assertEquals(1, MathExt.magnitude(1), 0.0001);
        assertEquals(0, MathExt.magnitude(0), 0.0001);
        assertEquals(10, MathExt.magnitude(6, 8), 0.0001);
        assertEquals(Math.sqrt(14), MathExt.magnitude(1, -2, 3), 0.0001);
    }

    @Test
    public void testSign(){
        assertEquals(1, MathExt.sign(10));
        assertEquals(0, MathExt.sign(0));
        assertEquals(-1, MathExt.sign(-10));
    }

    @Test
    public void testPercent(){
        assertEquals(0, MathExt.percent(10, 0), 0.0001);
        assertEquals(2.5, MathExt.percent(10, 25), 0.0001);
        assertEquals(5, MathExt.percent(10, 50), 0.0001);
        assertEquals(10, MathExt.percent(10, 100), 0.0001);
        assertEquals(15, MathExt.percent(10, 150), 0.0001);
    }

    @Test
    public void testSquare(){
        assertEquals(0, MathExt.square(0), 0.0001);
        assertEquals(1, MathExt.square(1), 0.0001);
        assertEquals(4, MathExt.square(2), 0.0001);
        assertEquals(4, MathExt.square(-2), 0.0001);
    }

    @Test
    public void testNormalize(){
        assertEquals(0, MathExt.normalize(0, 0, 100), 0.0001);
        assertEquals(0.5, MathExt.normalize(50, 0, 100), 0.0001);
        assertEquals(1, MathExt.normalize(100, 0, 100), 0.0001);
    }

    @Test (expected = OutOfBoundsException.class)
    public void testNormalizeAbove(){
        MathExt.normalize(150, 0, 100);
    }

    @Test (expected = OutOfBoundsException.class)
    public void testNormalizeBelow(){
        MathExt.normalize(-10, 0, 100);
    }

    @Test
    public void testNormalizeArray(){
        assertArrayEquals(new double[]{1}, MathExt.normalize(new double[]{1}), 0.0001);
        assertArrayEquals(new double[]{}, MathExt.normalize(new double[]{}), 0.0001);
        assertArrayEquals(new double[]{0}, MathExt.normalize(new double[]{0}), 0.0001);
        assertArrayEquals(new double[]{0, 1}, MathExt.normalize(new double[]{1, 10}), 0.0001);
        assertArrayEquals(new double[]{1, 0.5, 0}, MathExt.normalize(new double[]{-1, -2, -3}), 0.0001);
    }

    @Test
    public void testToList(){
        assertEquals(Collections.singletonList(1.0), MathExt.toList(new double[]{1}));
        assertEquals(Arrays.asList(1.0, 2.0, 3.0), MathExt.toList(new double[]{1, 2, 3}));
        assertEquals(new ArrayList<Double>(), MathExt.toList(new double[]{}));
    }

    @Test
    public void testToPrimitiveArray(){
        assertArrayEquals(new double[]{1}, MathExt.toPrimitiveArray(Collections.singletonList(1.0)), 0.0001);
        assertArrayEquals(new double[]{1, 2, 3}, MathExt.toPrimitiveArray(Arrays.asList(1.0, 2.0, 3.0)), 0.0001);
        assertArrayEquals(new double[]{}, MathExt.toPrimitiveArray(new ArrayList<>()), 0.0001);
    }

    @Test
    public void testSum(){
        assertEquals(1, MathExt.sum(new double[]{1}), 0.0001);
        assertEquals(0, MathExt.sum(new double[]{}), 0.0001);
        assertEquals(11, MathExt.sum(new double[]{1, 10}), 0.0001);
        assertEquals(-6, MathExt.sum(new double[]{-1, -2, -3}), 0.0001);
    }

    @Test
    public void testMin(){
        assertEquals(1, MathExt.min(new double[]{1}), 0.0001);
        assertEquals(Double.NEGATIVE_INFINITY, MathExt.min(new double[]{}), 0.0001);
        assertEquals(1, MathExt.min(new double[]{1, 10}), 0.0001);
        assertEquals(-3, MathExt.min(new double[]{-1, -2, -3}), 0.0001);
    }

    @Test
    public void testMax(){
        assertEquals(1, MathExt.max(new double[]{1}), 0.0001);
        assertEquals(Double.POSITIVE_INFINITY, MathExt.max(new double[]{}), 0.0001);
        assertEquals(10, MathExt.max(new double[]{1, 10}), 0.0001);
        assertEquals(-1, MathExt.max(new double[]{-1, -2, -3}), 0.0001);
    }

    @Test
    public void testToRange(){
        assertEquals(0, MathExt.toRange(0, 0, 100), 0.0001);
        assertEquals(50, MathExt.toRange(50, 0, 100), 0.0001);
        assertEquals(100, MathExt.toRange(100, 0, 100), 0.0001);
        assertEquals(0, MathExt.toRange(-10, 0, 100), 0.0001);
        assertEquals(100, MathExt.toRange(110, 0, 100), 0.0001);

    }

    @Test
    public void testRoundToInt(){
        assertEquals(0, MathExt.roundToInt(0));
        assertEquals(0, MathExt.roundToInt(0.2));
        assertEquals(1, MathExt.roundToInt(0.5));
        assertEquals(1, MathExt.roundToInt(0.7));
        assertEquals(1, MathExt.roundToInt(1));
    }

    @Test
    public void testRoundPlaces(){
        assertEquals(0.1, MathExt.roundPlaces(0.12, 1), 0.0001);
        assertEquals(0.0, MathExt.roundPlaces(0.02, 1), 0.0001);
        assertEquals(0.12, MathExt.roundPlaces(0.12, 2), 0.0001);
        assertEquals(0.12, MathExt.roundPlaces(0.12, 3), 0.0001);
        assertEquals(0.2, MathExt.roundPlaces(0.15, 1), 0.0001);
        assertEquals(0.15, MathExt.roundPlaces(0.15, 2), 0.0001);
        assertEquals(0, MathExt.roundPlaces(0.15, 0), 0.0001);
    }

    @Test
    public void testSnap(){
        assertEquals(0, MathExt.snap(2, 10), 0.0001);
        assertEquals(10, MathExt.snap(5, 10), 0.0001);
        assertEquals(20, MathExt.snap(25, 20), 0.0001);
    }

    @Test
    public void testDivisibleBy(){
        assertTrue(MathExt.divisibleBy(12, 6));
        assertTrue(MathExt.divisibleBy(1, 1));
        assertTrue(MathExt.divisibleBy(0, 6));
        assertTrue(MathExt.divisibleBy(-2, 2));

        assertFalse(MathExt.divisibleBy(1, 0));
        assertFalse(MathExt.divisibleBy(0, 0));
        assertFalse(MathExt.divisibleBy(1, 2));
        assertFalse(MathExt.divisibleBy(21, 6));
    }

    @Test
    public void testApproxEqual(){
        assertTrue(MathExt.approxEqual(1, 1, 0.0001));
        assertFalse(MathExt.approxEqual(1.2, 1, 0.0001));
        assertTrue(MathExt.approxEqual(1.2, 1, 0.2));
        assertFalse(MathExt.approxEqual(1.2, 1, 0.1));
    }

    @Test
    public void testSlopeFromAngleDegrees(){
        assertEquals(1, MathExt.slopeFromAngleDegrees(45), 0.0001);
        assertEquals(1.732, MathExt.slopeFromAngleDegrees(60), 0.001);
        assertEquals(1.5, MathExt.slopeFromAngleDegrees(56.31), 0.001);
    }

    @Test
    public void testSlopeFromAngleRadians(){
        assertEquals(1, MathExt.slopeFromAngleRadians(Math.toRadians(45)), 0.0001);
        assertEquals(1.732, MathExt.slopeFromAngleRadians(Math.toRadians(60)), 0.001);
        assertEquals(1.5, MathExt.slopeFromAngleRadians(Math.toRadians(56.31)), 0.001);
    }

    @Test
    public void testRateLimit(){
        assertEquals(0, MathExt.rateLimit(0.1, 0, 0), 0.0001);
        assertEquals(0.1, MathExt.rateLimit(0.1, 1, 0), 0.0001);
        assertEquals(0.3, MathExt.rateLimit(0.2, 1, 0.1), 0.0001);
        assertEquals(1.5, MathExt.rateLimit(1.5, 1.5, 0.3), 0.0001);
    }

    @Test
    public void testStandardDeviation(){
        assertEquals(Double.NaN, MathExt.standardDeviation(new double[]{1}), 0.0001);
        assertEquals(Double.NaN, MathExt.standardDeviation(new double[]{}), 0.0001);
        assertEquals(5.656854249, MathExt.standardDeviation(new double[]{2, 10}), 0.0001);
        assertEquals(1, MathExt.standardDeviation(new double[]{-1, -2, -3}), 0.0001);
        assertEquals(0, MathExt.standardDeviation(new double[]{0, 0, 0}), 0.0001);
    }

    @Test
    public void testCorrelation(){
        assertEquals(1, MathExt.correlation(new double[]{1, 2, 3, 4, 5, 6}, new double[]{4, 5, 6, 7, 8, 9}), 0.0001);
        assertEquals(-1, MathExt.correlation(new double[]{6, 5, 4, 3, 2, 1}, new double[]{4, 5, 6, 7, 8, 9}), 0.0001);
        assertEquals(-0.0275, MathExt.correlation(new double[]{1, 4, 3, 4, 5, 0}, new double[]{4, 5, 6, 7, 8, 9}), 0.0001);
    }

    @Test
    public void testMean(){
        assertEquals(1, MathExt.mean(new double[]{1}), 0.0001);
        assertEquals(Double.NaN, MathExt.mean(new double[]{}), 0.0001);
        assertEquals(6, MathExt.mean(new double[]{2, 10}), 0.0001);
        assertEquals(-2, MathExt.mean(new double[]{-1, -2, -3}), 0.0001);
        assertEquals(0, MathExt.mean(new double[]{0, 0, 0}), 0.0001);
    }

}