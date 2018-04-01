package com.thegongoliers.input.accelerometer;

import com.kylecorry.geometry.Quaternion;
import com.kylecorry.geometry.Vector3;
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
    public void testTilt(){
        Vector3 angle = new Vector3(0, 0, 0);
        // Y axis
        mockAccelerometer.setY(1);
        angle.y = 90;
        vectorsEqual(angle, tiltSensor.getTilt(), 0.0001);

        mockAccelerometer.setY(0.5);
        angle.y = 30;
        vectorsEqual(angle, tiltSensor.getTilt(), 0.0001);

        mockAccelerometer.setY(0);
        angle.y = 0;
        vectorsEqual(angle, tiltSensor.getTilt(), 0.0001);

        // Z axis
        mockAccelerometer.setZ(1);
        angle.z = 90;
        vectorsEqual(angle, tiltSensor.getTilt(), 0.0001);

        mockAccelerometer.setZ(-0.5);
        angle.z = -30;
        vectorsEqual(angle, tiltSensor.getTilt(), 0.0001);

        mockAccelerometer.setZ(0);
        angle.z = 0;
        vectorsEqual(angle, tiltSensor.getTilt(), 0.0001);

        // X axis
        mockAccelerometer.setX(1);
        angle.x = 90;
        vectorsEqual(angle, tiltSensor.getTilt(), 0.0001);

        mockAccelerometer.setX(0.5);
        angle.x = 30;
        vectorsEqual(angle, tiltSensor.getTilt(), 0.0001);

        mockAccelerometer.setX(0);
        angle.x = 0;
        vectorsEqual(angle, tiltSensor.getTilt(), 0.0001);
    }

    @Test
    public void testCalibration(){
        Vector3 angle = new Vector3(0, 0, 0);

        mockAccelerometer.setY(0.5); // Accel. on a 30 deg tilt to start

        tiltSensor.calibrate();

        angle.y = 0;
        vectorsEqual(angle, tiltSensor.getTilt(), 0.0001);

        mockAccelerometer.setY(0.866025404);
        angle.y = 30;
        vectorsEqual(angle, tiltSensor.getTilt(), 0.0001);

        mockAccelerometer.setY(1);
        angle.y = 60;
        vectorsEqual(angle, tiltSensor.getTilt(), 0.0001);
    }

    private void vectorsEqual(Vector3 v, Vector3 v2, double tolerance){
        assertEquals(v.x, v2.x, tolerance);
        assertEquals(v.y, v2.y, tolerance);
        assertEquals(v.z, v2.z, tolerance);
    }

}