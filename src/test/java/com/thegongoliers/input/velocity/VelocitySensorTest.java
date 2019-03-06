package com.thegongoliers.input.velocity;

import com.thegongoliers.mockHardware.input.MockClock;
import com.thegongoliers.mockHardware.input.MockPotentiometer;
import com.thegongoliers.mockHardware.input.MockSwitch;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class VelocitySensorTest {

    private VelocitySensor velocitySensor;
    private MockPotentiometer potentiometer;
    private MockClock clock;

    @Before
    public void setup(){
        potentiometer = new MockPotentiometer();
        potentiometer.setAngle(0.0);

        clock = new MockClock();
        clock.setTime(0.0);

        velocitySensor = new VelocitySensor(potentiometer::get, clock);
    }

    @Test
    public void test(){
        assertEquals(0.0, velocitySensor.getVelocity(), 0.0001);

        clock.setTime(1.0);
        potentiometer.setAngle(90);

        assertEquals(90, velocitySensor.getVelocity(), 0.0001);
        assertEquals(0, velocitySensor.getVelocity(), 0.0001);

        clock.setTime(1.5);
        potentiometer.setAngle(-90);

        assertEquals(-360, velocitySensor.getVelocity(), 0.0001);
        assertEquals(0, velocitySensor.getVelocity(), 0.0001);
    }

}