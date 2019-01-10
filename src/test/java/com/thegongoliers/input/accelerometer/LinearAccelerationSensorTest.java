package com.thegongoliers.input.accelerometer;

import com.thegongoliers.mockHardware.input.MockAccelerometer;
import org.junit.Test;

import static org.junit.Assert.*;

public class LinearAccelerationSensorTest {

    @Test
    public void testLinear(){
        MockAccelerometer mockAccelerometer = new MockAccelerometer();
        mockAccelerometer.setZ(9.81);

        LinearAccelerationSensor linearAccelerationSensor = new LinearAccelerationSensor(mockAccelerometer);

        // Gravity on Z axis only
        assertEquals(0, linearAccelerationSensor.getZ(), 0.0001);
        assertEquals(0, linearAccelerationSensor.getX(), 0.0001);
        assertEquals(0, linearAccelerationSensor.getY(), 0.0001);

        // Rotate accelerometer to X
        mockAccelerometer.setX(9.81);
        mockAccelerometer.setZ(0);

        assertEquals(-9.81 * 0.8, linearAccelerationSensor.getZ(), 0.0001);
        assertEquals(9.81 - (9.81 * 0.2 * 0.8 + 9.81 * 0.2), linearAccelerationSensor.getX(), 0.0001);
        assertEquals(0, linearAccelerationSensor.getY(), 0.0001);
    }

}