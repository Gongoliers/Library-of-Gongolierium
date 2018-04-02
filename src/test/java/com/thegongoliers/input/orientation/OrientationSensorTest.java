package com.thegongoliers.input.orientation;

import com.kylecorry.geometry.Orientation;
import com.thegongoliers.mockHardware.input.MockAccelerometer;
import com.thegongoliers.mockHardware.input.MockGyro;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class OrientationSensorTest {

    private MockAccelerometer mockAccelerometer;
    private MockGyro mockGyro;
    private OrientationSensor orientationSensor;
    private Orientation orientation;

    @Before
    public void setup(){
        mockAccelerometer = new MockAccelerometer();
        mockGyro = new MockGyro();
        orientationSensor = new OrientationSensor(mockAccelerometer, mockGyro);
        orientation = new Orientation(0, 0, 0);
    }

    @Test
    public void testOrientation(){
        orientationAlmostEquals(orientation, orientationSensor.getOrientation(), 0.0001);

        mockGyro.setAngle(90);
        orientation.setYaw(90);
        orientationAlmostEquals(orientation, orientationSensor.getOrientation(), 0.0001);

        mockAccelerometer.setY(0.5);
        mockAccelerometer.setZ(0.5);
        orientation.setRoll(45);
        orientationAlmostEquals(orientation, orientationSensor.getOrientation(), 0.0001);

        mockAccelerometer.setY(0);
        mockAccelerometer.setZ(0.5);
        mockAccelerometer.setX(-0.5);
        orientation.setPitch(45);
        orientation.setRoll(0);
        orientationAlmostEquals(orientation, orientationSensor.getOrientation(), 0.0001);
    }


    private void orientationAlmostEquals(Orientation o1, Orientation o2, double tolerance){
        assertEquals(o1.getRoll(), o2.getRoll(), tolerance);
        assertEquals(o1.getPitch(), o2.getPitch(), tolerance);
        assertEquals(o1.getYaw(), o2.getYaw(), tolerance);
    }

}
