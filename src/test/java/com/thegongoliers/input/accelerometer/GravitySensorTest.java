package com.thegongoliers.input.accelerometer;

import com.thegongoliers.mockHardware.input.MockAccelerometer;
import org.junit.Test;

import static org.junit.Assert.*;

public class GravitySensorTest {


    @Test
    public void testGravity(){
        MockAccelerometer mockAccelerometer = new MockAccelerometer();
        mockAccelerometer.setZ(9.81);

        GravitySensor gravitySensor = new GravitySensor(mockAccelerometer);

        // Gravity on Z axis only
        assertEquals(9.81, gravitySensor.getZ(), 0.0001);
        assertEquals(0, gravitySensor.getX(), 0.0001);
        assertEquals(0, gravitySensor.getY(), 0.0001);

        // Rotate accelerometer to X
        mockAccelerometer.setX(9.81);
        mockAccelerometer.setZ(0);

        assertEquals(9.81 * 0.8, gravitySensor.getZ(), 0.0001);
        assertEquals(9.81 * 0.2, gravitySensor.getX(), 0.0001);
        assertEquals(0, gravitySensor.getY(), 0.0001);

    }


}