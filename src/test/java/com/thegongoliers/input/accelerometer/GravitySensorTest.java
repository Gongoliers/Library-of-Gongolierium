package com.thegongoliers.input.accelerometer;

import org.junit.Test;

import edu.wpi.first.wpilibj.interfaces.Accelerometer;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class GravitySensorTest {


    @Test
    public void testGravity(){
        Accelerometer mockAccelerometer = mock(Accelerometer.class);
        setAcceleration(mockAccelerometer, 0, 0, 9.81);

        GravitySensor gravitySensor = new GravitySensor(mockAccelerometer);

        // Gravity on Z axis only
        assertEquals(9.81, gravitySensor.getZ(), 0.0001);
        assertEquals(0, gravitySensor.getX(), 0.0001);
        assertEquals(0, gravitySensor.getY(), 0.0001);

        // Rotate accelerometer to X
        setAcceleration(mockAccelerometer, 9.81, 0, 0);

        assertEquals(9.81 * 0.8, gravitySensor.getZ(), 0.0001);
        assertEquals(9.81 * 0.2, gravitySensor.getX(), 0.0001);
        assertEquals(0, gravitySensor.getY(), 0.0001);

    }

    private void setAcceleration(Accelerometer accelerometer, double x, double y, double z){
        when(accelerometer.getX()).thenReturn(x);
        when(accelerometer.getY()).thenReturn(y);
        when(accelerometer.getZ()).thenReturn(z);
    }


}