package com.thegongoliers.input.orientation;

import com.kylecorry.geometry.Orientation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import edu.wpi.first.wpilibj.interfaces.Accelerometer;
import edu.wpi.first.wpilibj.interfaces.Gyro;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class OrientationSensorTest {

    private Accelerometer mockAccelerometer;
    private Gyro mockGyro;
    private OrientationSensor orientationSensor;
    private Orientation orientation;

    @BeforeEach
    public void setup(){
        mockAccelerometer = mock(Accelerometer.class);
        mockGyro = mock(Gyro.class);
        orientationSensor = new OrientationSensor(mockAccelerometer, mockGyro);
        orientation = new Orientation(0, 0, 0);
    }

    @Test
    public void testOrientation(){
        orientationAlmostEquals(orientation, orientationSensor.getOrientation(), 0.0001);

        when(mockGyro.getAngle()).thenReturn(90.0);
        orientation.setYaw(90);
        orientationAlmostEquals(orientation, orientationSensor.getOrientation(), 0.0001);

        when(mockAccelerometer.getY()).thenReturn(0.5);
        when(mockAccelerometer.getZ()).thenReturn(0.5);
        orientation.setRoll(45);
        orientationAlmostEquals(orientation, orientationSensor.getOrientation(), 0.0001);

        when(mockAccelerometer.getY()).thenReturn(0.0);
        when(mockAccelerometer.getZ()).thenReturn(0.5);
        when(mockAccelerometer.getX()).thenReturn(-0.5);
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
