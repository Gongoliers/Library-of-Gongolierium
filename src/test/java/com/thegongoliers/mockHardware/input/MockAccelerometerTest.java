package com.thegongoliers.mockHardware.input;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class MockAccelerometerTest {

    private MockAccelerometer accelerometer;

    @Before
    public void setup(){
        accelerometer = new MockAccelerometer();
    }

    @Test
    public void test(){
        accelerometer.setX(1);
        accelerometer.setY(2);
        accelerometer.setZ(-3);

        assertEquals(1, accelerometer.getX(), 0);
        assertEquals(2, accelerometer.getY(), 0);
        assertEquals(-3, accelerometer.getZ(), 0);
    }


}