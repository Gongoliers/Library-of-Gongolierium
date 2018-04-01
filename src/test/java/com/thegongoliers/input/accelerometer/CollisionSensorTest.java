package com.thegongoliers.input.accelerometer;

import com.thegongoliers.mockHardware.input.MockAccelerometer;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class CollisionSensorTest {

    private CollisionSensor collisionSensor;
    private MockAccelerometer mockAccelerometer;

    @Before
    public void setup(){
        mockAccelerometer = new MockAccelerometer();
        collisionSensor = new CollisionSensor(mockAccelerometer, 10);
    }

    @Test
    public void test(){
        mockAccelerometer.setX(0);
        mockAccelerometer.setY(0);
        mockAccelerometer.setZ(0);

        assertFalse(collisionSensor.isTriggered());

        mockAccelerometer.setX(10);
        assertTrue(collisionSensor.isTriggered());

        mockAccelerometer.setX(0);
        mockAccelerometer.setY(10);
        assertTrue(collisionSensor.isTriggered());

        mockAccelerometer.setY(0);
        mockAccelerometer.setZ(10);
        assertTrue(collisionSensor.isTriggered());

        mockAccelerometer.setZ(0);
        mockAccelerometer.setX(5);
        assertFalse(collisionSensor.isTriggered());

        mockAccelerometer.setY(5);
        mockAccelerometer.setZ(8);
        assertTrue(collisionSensor.isTriggered());

    }


}