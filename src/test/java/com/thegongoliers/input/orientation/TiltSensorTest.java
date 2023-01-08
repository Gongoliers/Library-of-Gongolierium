package com.thegongoliers.input.orientation;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import edu.wpi.first.wpilibj.interfaces.Accelerometer;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class TiltSensorTest {

    private TiltSensor tiltSensor;
    private Accelerometer mockAccelerometer;

    @BeforeEach
    public void setup(){
        mockAccelerometer = mock(Accelerometer.class);
        tiltSensor = new TiltSensor(mockAccelerometer);
    }

    @Test
    public void testRoll(){
        when(mockAccelerometer.getY()).thenReturn(0.5);
        when(mockAccelerometer.getZ()).thenReturn(0.5);
        assertEquals(45, tiltSensor.getRoll(), 0.0001);

        when(mockAccelerometer.getY()).thenReturn(1.0);
        when(mockAccelerometer.getZ()).thenReturn(0.0);
        assertEquals(90, tiltSensor.getRoll(), 0.0001);

        when(mockAccelerometer.getY()).thenReturn(-1.0);
        when(mockAccelerometer.getZ()).thenReturn(0.0);
        assertEquals(-90, tiltSensor.getRoll(), 0.0001);

        when(mockAccelerometer.getY()).thenReturn(0.0);
        when(mockAccelerometer.getZ()).thenReturn(1.0);
        assertEquals(0, tiltSensor.getRoll(), 0.0001);
    }

    @Test
    public void testPitch(){
        when(mockAccelerometer.getX()).thenReturn(-0.5);
        when(mockAccelerometer.getZ()).thenReturn(0.5);
        assertEquals(45, tiltSensor.getPitch(), 0.0001);

        when(mockAccelerometer.getX()).thenReturn(1.0);
        when(mockAccelerometer.getZ()).thenReturn(0.0);
        assertEquals(-90, tiltSensor.getPitch(), 0.0001);

        when(mockAccelerometer.getX()).thenReturn(-1.0);
        when(mockAccelerometer.getZ()).thenReturn(0.0);
        assertEquals(90, tiltSensor.getPitch(), 0.0001);

        when(mockAccelerometer.getX()).thenReturn(0.0);
        when(mockAccelerometer.getZ()).thenReturn(1.0);
        assertEquals(0, tiltSensor.getPitch(), 0.0001);
    }

    @Test
    public void testCalibration(){

        when(mockAccelerometer.getX()).thenReturn(-0.5); // Accel. on a 45 deg pitch to start
        when(mockAccelerometer.getZ()).thenReturn(0.5);

        assertEquals(45, tiltSensor.getPitch(), 0.0001);

        tiltSensor.calibrate();

        when(mockAccelerometer.getX()).thenReturn(0.5);
        when(mockAccelerometer.getZ()).thenReturn(0.5);
        assertEquals(-90, tiltSensor.getPitch(), 0.0001);

        when(mockAccelerometer.getX()).thenReturn(-0.5);
        when(mockAccelerometer.getZ()).thenReturn(0.5);
        assertEquals(0, tiltSensor.getPitch(), 0.0001);

        when(mockAccelerometer.getX()).thenReturn(-1.0);
        when(mockAccelerometer.getZ()).thenReturn(0.0);
        assertEquals(45, tiltSensor.getPitch(), 0.0001);

        when(mockAccelerometer.getX()).thenReturn(1.0);
        when(mockAccelerometer.getZ()).thenReturn(0.0);
        assertEquals(-135, tiltSensor.getPitch(), 0.0001);

        when(mockAccelerometer.getY()).thenReturn(0.0);
        when(mockAccelerometer.getX()).thenReturn(0.0);
        when(mockAccelerometer.getZ()).thenReturn(0.0);

        tiltSensor.calibrate();

        when(mockAccelerometer.getY()).thenReturn(1.0);

        assertEquals(90, tiltSensor.getRoll(), 0.0001);

        tiltSensor.calibrate();

        assertEquals(0, tiltSensor.getRoll(), 0.0001);

        when(mockAccelerometer.getY()).thenReturn(0.5);
        when(mockAccelerometer.getZ()).thenReturn(0.5);
        assertEquals(-45, tiltSensor.getRoll(), 0.0001);

    }


}