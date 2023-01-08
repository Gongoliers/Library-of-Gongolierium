package com.thegongoliers.input.accelerometer;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;import edu.wpi.first.wpilibj.interfaces.Accelerometer;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class LinearAccelerationSensorTest {

    @Test
    public void testLinear(){
        Accelerometer mockAccelerometer = mock(Accelerometer.class);
        when(mockAccelerometer.getZ()).thenReturn(9.81);

        LinearAccelerationSensor linearAccelerationSensor = new LinearAccelerationSensor(mockAccelerometer);

        // Gravity on Z axis only
        assertEquals(0, linearAccelerationSensor.getZ(), 0.0001);
        assertEquals(0, linearAccelerationSensor.getX(), 0.0001);
        assertEquals(0, linearAccelerationSensor.getY(), 0.0001);

        // Rotate accelerometer to X
        when(mockAccelerometer.getX()).thenReturn(9.81);
        when(mockAccelerometer.getZ()).thenReturn(0.0);

        assertEquals(-9.81 * 0.8, linearAccelerationSensor.getZ(), 0.0001);
        assertEquals(9.81 - 9.81 * 0.2, linearAccelerationSensor.getX(), 0.0001);
        assertEquals(0, linearAccelerationSensor.getY(), 0.0001);
    }

}