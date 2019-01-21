package com.thegongoliers.input.odometry;

import org.junit.Test;

import static org.junit.Assert.*;

public class OdometryTest {

    @Test
    public void getsCenterDistance() {
        double distance = Odometry.getDistance(1, 0);
        assertEquals(0.5, distance, 0.0001);

        distance = Odometry.getDistance(0, 1);
        assertEquals(0.5, distance, 0.0001);

        distance = Odometry.getDistance(10, 10);
        assertEquals(10, distance, 0.0001);

        distance = Odometry.getDistance(-10, -10);
        assertEquals(-10, distance, 0.0001);
    }
}