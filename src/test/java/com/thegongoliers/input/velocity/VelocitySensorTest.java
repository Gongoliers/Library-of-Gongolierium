package com.thegongoliers.input.velocity;

import com.thegongoliers.input.time.Clock;
import org.junit.Before;
import org.junit.Test;

import edu.wpi.first.wpilibj.interfaces.Potentiometer;

import static org.mockito.Mockito.*;

import static org.junit.Assert.*;

public class VelocitySensorTest {

    private VelocitySensor velocitySensor;
    private Potentiometer potentiometer;
    private Clock clock;

    @Before
    public void setup(){
        potentiometer = mock(Potentiometer.class);
        when(potentiometer.get()).thenReturn(0.0);

        clock = mock(Clock.class);
        when(clock.getTime()).thenReturn(0.0);

        velocitySensor = new VelocitySensor(potentiometer::get, clock);
    }

    @Test
    public void test(){
        assertEquals(0.0, velocitySensor.getVelocity(), 0.0001);

        when(clock.getTime()).thenReturn(1.0);
        when(potentiometer.get()).thenReturn(90.0);

        assertEquals(90, velocitySensor.getVelocity(), 0.0001);
        assertEquals(0, velocitySensor.getVelocity(), 0.0001);

        when(clock.getTime()).thenReturn(1.5);
        when(potentiometer.get()).thenReturn(-90.0);

        assertEquals(-360, velocitySensor.getVelocity(), 0.0001);
        assertEquals(0, velocitySensor.getVelocity(), 0.0001);
    }

}