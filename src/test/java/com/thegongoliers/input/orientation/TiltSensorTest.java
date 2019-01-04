package com.thegongoliers.input.orientation;

import com.thegongoliers.mockHardware.input.MockAccelerometer;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class TiltSensorTest {

    private TiltSensor tiltSensor;
    private MockAccelerometer mockAccelerometer;

    @Before
    public void setup(){
        mockAccelerometer = new MockAccelerometer();
        tiltSensor = new TiltSensor(mockAccelerometer);
    }

    @Test
    public void testRoll(){
        mockAccelerometer.setY(0.5);
        mockAccelerometer.setZ(0.5);
        assertEquals(45, tiltSensor.getRoll(), 0.0001);

        mockAccelerometer.setY(1);
        mockAccelerometer.setZ(0);
        assertEquals(90, tiltSensor.getRoll(), 0.0001);

        mockAccelerometer.setY(-1);
        mockAccelerometer.setZ(0);
        assertEquals(-90, tiltSensor.getRoll(), 0.0001);

        mockAccelerometer.setY(0);
        mockAccelerometer.setZ(1);
        assertEquals(0, tiltSensor.getRoll(), 0.0001);
    }

    @Test
    public void testPitch(){
        mockAccelerometer.setX(-0.5);
        mockAccelerometer.setZ(0.5);
        assertEquals(45, tiltSensor.getPitch(), 0.0001);

        mockAccelerometer.setX(1);
        mockAccelerometer.setZ(0);
        assertEquals(-90, tiltSensor.getPitch(), 0.0001);

        mockAccelerometer.setX(-1);
        mockAccelerometer.setZ(0);
        assertEquals(90, tiltSensor.getPitch(), 0.0001);

        mockAccelerometer.setX(0);
        mockAccelerometer.setZ(1);
        assertEquals(0, tiltSensor.getPitch(), 0.0001);
    }

    @Test
    public void testCalibration(){

        mockAccelerometer.setX(-0.5); // Accel. on a 45 deg pitch to start
        mockAccelerometer.setZ(0.5);

        assertEquals(45, tiltSensor.getPitch(), 0.0001);

        tiltSensor.calibrate();

        mockAccelerometer.setX(0.5);
        mockAccelerometer.setZ(0.5);
        assertEquals(-90, tiltSensor.getPitch(), 0.0001);

        mockAccelerometer.setX(-0.5);
        mockAccelerometer.setZ(0.5);
        assertEquals(0, tiltSensor.getPitch(), 0.0001);

        mockAccelerometer.setX(-1);
        mockAccelerometer.setZ(0);
        assertEquals(45, tiltSensor.getPitch(), 0.0001);

        mockAccelerometer.setX(1);
        mockAccelerometer.setZ(0);
        assertEquals(-135, tiltSensor.getPitch(), 0.0001);

        mockAccelerometer.setY(0);
        mockAccelerometer.setX(0);
        mockAccelerometer.setZ(0);

        tiltSensor.calibrate();

        mockAccelerometer.setY(1);

        assertEquals(90, tiltSensor.getRoll(), 0.0001);

        tiltSensor.calibrate();

        assertEquals(0, tiltSensor.getRoll(), 0.0001);

        mockAccelerometer.setY(0.5);
        mockAccelerometer.setZ(0.5);
        assertEquals(-45, tiltSensor.getRoll(), 0.0001);

    }


}