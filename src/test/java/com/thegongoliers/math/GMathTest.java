package com.thegongoliers.math;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class GMathTest {

    @Test
    public void testMagnitude(){
        assertEquals(1, GMath.magnitude(1), 0.0001);
        assertEquals(0, GMath.magnitude(0), 0.0001);
        assertEquals(10, GMath.magnitude(6, 8), 0.0001);
        assertEquals(Math.sqrt(14), GMath.magnitude(1, -2, 3), 0.0001);
    }

    @Test
    public void testSign(){
        assertEquals(1, GMath.sign(10));
        assertEquals(1, GMath.sign(0));
        assertEquals(-1, GMath.sign(-10));
    }

    @Test
    public void testSignPreservingPower(){
        assertEquals(0, GMath.signPreservingPower(0, 2), 0.0001);
        assertEquals(4, GMath.signPreservingPower(2, 2), 0.0001);
        assertEquals(-4, GMath.signPreservingPower(-2, 2), 0.0001);
        assertEquals(-8, GMath.signPreservingPower(-2, 3), 0.0001);
    }

    @Test
    public void testInverseLerp(){
        assertEquals(0, GMath.inverseLerp(0, 100, 0), 0.0001);
        assertEquals(0.5, GMath.inverseLerp(0, 100, 50), 0.0001);
        assertEquals(1, GMath.inverseLerp(0, 100, 100), 0.0001);
        assertEquals(1, GMath.inverseLerp(0, 100, 110), 0.0001);
        assertEquals(0, GMath.inverseLerp(0, 100, -10), 0.0001);
    }

    @Test
    public void testSum(){
        assertEquals(1, GMath.sum(new double[]{1}), 0.0001);
        assertEquals(0, GMath.sum(new double[]{}), 0.0001);
        assertEquals(11, GMath.sum(new double[]{1, 10}), 0.0001);
        assertEquals(-6, GMath.sum(new double[]{-1, -2, -3}), 0.0001);
    }

    @Test
    public void testMin(){
        assertEquals(1, GMath.min(new double[]{1}), 0.0001);
        assertEquals(0, GMath.min(new double[]{}), 0.0001);
        assertEquals(1, GMath.min(new double[]{1, 10}), 0.0001);
        assertEquals(-3, GMath.min(new double[]{-1, -2, -3}), 0.0001);
    }

    @Test
    public void testMax(){
        assertEquals(1, GMath.max(new double[]{1}), 0.0001);
        assertEquals(0, GMath.max(new double[]{}), 0.0001);
        assertEquals(10, GMath.max(new double[]{1, 10}), 0.0001);
        assertEquals(-1, GMath.max(new double[]{-1, -2, -3}), 0.0001);
    }

    @Test
    public void testLerp(){
        assertEquals(0, GMath.lerp(0, 100, 0), 0.0001);
        assertEquals(50, GMath.lerp(0, 100, 0.5), 0.0001);
        assertEquals(100, GMath.lerp(0, 100, 1), 0.0001);
        assertEquals(0, GMath.lerp(0, 100, -0.1), 0.0001);
        assertEquals(100, GMath.lerp(0, 100, 1.1), 0.0001);
    }

    @Test
    public void testLerpUnclamped(){
        assertEquals(0, GMath.lerpUnclamped(0, 100, 0), 0.0001);
        assertEquals(50, GMath.lerpUnclamped(0, 100, 0.5), 0.0001);
        assertEquals(100, GMath.lerpUnclamped(0, 100, 1), 0.0001);
        assertEquals(-10, GMath.lerpUnclamped(0, 100, -0.1), 0.0001);
        assertEquals(110, GMath.lerpUnclamped(0, 100, 1.1), 0.0001);
    }

    @Test
    public void testRepeat(){
        assertEquals(0.5, GMath.repeat(3, 2.5), 0.001);
        assertEquals(0, GMath.repeat(5, 2.5), 0.001);
    }

    @Test
    public void testRoundToInt(){
        assertEquals(0, GMath.roundToInt(0));
        assertEquals(0, GMath.roundToInt(0.2));
        assertEquals(1, GMath.roundToInt(0.5));
        assertEquals(1, GMath.roundToInt(0.7));
        assertEquals(1, GMath.roundToInt(1));
    }

    @Test
    public void testRoundPlaces(){
        assertEquals(0.1, GMath.roundPlaces(0.12, 1), 0.0001);
        assertEquals(0.0, GMath.roundPlaces(0.02, 1), 0.0001);
        assertEquals(0.12, GMath.roundPlaces(0.12, 2), 0.0001);
        assertEquals(0.12, GMath.roundPlaces(0.12, 3), 0.0001);
        assertEquals(0.2, GMath.roundPlaces(0.15, 1), 0.0001);
        assertEquals(0.15, GMath.roundPlaces(0.15, 2), 0.0001);
        assertEquals(0, GMath.roundPlaces(0.15, 0), 0.0001);
    }

    @Test
    public void testSnap(){
        assertEquals(0, GMath.snap(2, 10), 0.0001);
        assertEquals(10, GMath.snap(5, 10), 0.0001);
        assertEquals(20, GMath.snap(25, 20), 0.0001);
    }

    @Test
    public void testDivisibleBy(){
        assertTrue(GMath.divisibleBy(12, 6));
        assertTrue(GMath.divisibleBy(1, 1));
        assertTrue(GMath.divisibleBy(0, 6));
        assertTrue(GMath.divisibleBy(-2, 2));

        assertFalse(GMath.divisibleBy(1, 0));
        assertFalse(GMath.divisibleBy(0, 0));
        assertFalse(GMath.divisibleBy(1, 2));
        assertFalse(GMath.divisibleBy(21, 6));
    }

    @Test
    public void testApproxEqual(){
        assertTrue(GMath.approximately(1, 1, 0.0001));
        assertFalse(GMath.approximately(1.2, 1, 0.0001));
        assertTrue(GMath.approximately(1.2, 1, 0.2));
        assertFalse(GMath.approximately(1.2, 1, 0.1));
    }

    @Test
    public void testApproximately(){
        assertTrue(GMath.approximately(1, 1));
        assertFalse(GMath.approximately(1.2, 1));
        assertTrue(GMath.approximately(1, 10 / 10.0));
    }

    @Test
    public void testDeltaAngle(){
        assertEquals(90, GMath.deltaAngle(1080, 90), 0.0001);
        assertEquals(180, GMath.deltaAngle(270, 90), 0.0001);
        assertEquals(-170, GMath.deltaAngle(260, 90), 0.0001);
        assertEquals(170, GMath.deltaAngle(280, 90), 0.0001);
        assertEquals(1, GMath.deltaAngle(1000, 1001), 0.0001);
        assertEquals(-1, GMath.deltaAngle(1000, 999), 0.0001);

        assertEquals(0, GMath.deltaAngle(-360, 360), 0.0001);
    }

    @Test
    public void testSlopeFromAngleDegrees(){
        assertEquals(1, GMath.slopeFromAngleDegrees(45), 0.0001);
        assertEquals(1.732, GMath.slopeFromAngleDegrees(60), 0.001);
        assertEquals(1.5, GMath.slopeFromAngleDegrees(56.31), 0.001);
    }

    @Test
    public void testSlopeFromAngleRadians(){
        assertEquals(1, GMath.slopeFromAngleRadians(Math.toRadians(45)), 0.0001);
        assertEquals(1.732, GMath.slopeFromAngleRadians(Math.toRadians(60)), 0.001);
        assertEquals(1.5, GMath.slopeFromAngleRadians(Math.toRadians(56.31)), 0.001);
    }

    @Test
    public void testRateLimit(){
        assertEquals(0, GMath.rateLimit(0.1, 0, 0), 0.0001);
        assertEquals(0.1, GMath.rateLimit(0.1, 1, 0), 0.0001);
        assertEquals(0.3, GMath.rateLimit(0.2, 1, 0.1), 0.0001);
        assertEquals(1.5, GMath.rateLimit(1.5, 1.5, 0.3), 0.0001);
    }

    @Test
    public void testMoveTowards(){
        assertEquals(0, GMath.moveTowards(0, 0, 0.1), 0.0001);
        assertEquals(0.1, GMath.moveTowards(0, 1, 0.1), 0.0001);
        assertEquals(0.3, GMath.moveTowards(0.1, 1, 0.2), 0.0001);
        assertEquals(1.5, GMath.moveTowards(0.3, 1.5, 1.5), 0.0001);
    }

    @Test
    public void testStandardDeviation(){
        assertEquals(Double.NaN, GMath.standardDeviation(new double[]{1}), 0.0001);
        assertEquals(Double.NaN, GMath.standardDeviation(new double[]{}), 0.0001);
        assertEquals(5.656854249, GMath.standardDeviation(new double[]{2, 10}), 0.0001);
        assertEquals(1, GMath.standardDeviation(new double[]{-1, -2, -3}), 0.0001);
        assertEquals(0, GMath.standardDeviation(new double[]{0, 0, 0}), 0.0001);
    }

    @Test
    public void testCorrelation(){
        assertEquals(1, GMath.correlation(new double[]{1, 2, 3, 4, 5, 6}, new double[]{4, 5, 6, 7, 8, 9}), 0.0001);
        assertEquals(-1, GMath.correlation(new double[]{6, 5, 4, 3, 2, 1}, new double[]{4, 5, 6, 7, 8, 9}), 0.0001);
        assertEquals(-0.0275, GMath.correlation(new double[]{1, 4, 3, 4, 5, 0}, new double[]{4, 5, 6, 7, 8, 9}), 0.0001);
    }

    @Test
    public void testMean(){
        assertEquals(1, GMath.mean(new double[]{1}), 0.0001);
        assertEquals(Double.NaN, GMath.mean(new double[]{}), 0.0001);
        assertEquals(6, GMath.mean(new double[]{2, 10}), 0.0001);
        assertEquals(-2, GMath.mean(new double[]{-1, -2, -3}), 0.0001);
        assertEquals(0, GMath.mean(new double[]{0, 0, 0}), 0.0001);
    }

}