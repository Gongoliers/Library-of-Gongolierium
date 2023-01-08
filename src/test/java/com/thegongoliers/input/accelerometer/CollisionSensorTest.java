package com.thegongoliers.input.accelerometer;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import edu.wpi.first.wpilibj.interfaces.Accelerometer;

import static org.mockito.Mockito.*;

public class CollisionSensorTest {

    private CollisionSensor collisionSensor;
    private Accelerometer mockAccelerometer;

    @BeforeEach
    public void setup(){
        mockAccelerometer = mock(Accelerometer.class);
        collisionSensor = new CollisionSensor(mockAccelerometer, 10);
    }

    @Test
    public void test(){
        setAcceleration(mockAccelerometer, 0, 0, 0);
        assertFalse(collisionSensor.isTriggered());

        setAcceleration(mockAccelerometer, 10, 0, 0);
        assertTrue(collisionSensor.isTriggered());

        setAcceleration(mockAccelerometer, 0, 10, 0);
        assertTrue(collisionSensor.isTriggered());

        setAcceleration(mockAccelerometer, 0, 0, 10);
        assertTrue(collisionSensor.isTriggered());

        setAcceleration(mockAccelerometer, 5, 0, 0);
        assertFalse(collisionSensor.isTriggered());

        setAcceleration(mockAccelerometer, 5, 5, 8);
        assertTrue(collisionSensor.isTriggered());

    }

    private void setAcceleration(Accelerometer accelerometer, double x, double y, double z){
        when(accelerometer.getX()).thenReturn(x);
        when(accelerometer.getY()).thenReturn(y);
        when(accelerometer.getZ()).thenReturn(z);
    }


}