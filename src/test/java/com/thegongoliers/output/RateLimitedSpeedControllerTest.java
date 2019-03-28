package com.thegongoliers.output;

import com.thegongoliers.GongolieriumException;
import edu.wpi.first.wpilibj.SpeedController;
import org.junit.Before;
import org.junit.Test;
import org.mockito.AdditionalMatchers;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class RateLimitedSpeedControllerTest {

    private SpeedController speedController;
    private SpeedController rateLimitedController;

    @Before
    public void setup(){
        speedController = mock(SpeedController.class);
        when(speedController.get()).thenReturn(0.0);
        rateLimitedController = new RateLimitedSpeedController(speedController, 0.1);
    }

    @Test
    public void limitsChange(){
        rateLimitedController.set(1.0);
        verify(speedController).set(0.1);
        when(speedController.get()).thenReturn(0.1);

        rateLimitedController.set(0.15);
        verify(speedController).set(0.15);
        when(speedController.get()).thenReturn(0.15);

        rateLimitedController.set(0.0);
        verify(speedController).set(AdditionalMatchers.eq(0.05, 0.0001));
        when(speedController.get()).thenReturn(0.05);

        rateLimitedController.set(-1.0);
        verify(speedController).set(AdditionalMatchers.eq(-0.05, 0.0001));
        when(speedController.get()).thenReturn(-1.0);
    }

    @Test
    public void getWorks(){
        assertEquals(0.0, rateLimitedController.get(), 0.0001);

        when(speedController.get()).thenReturn(1.0);
        assertEquals(1.0, rateLimitedController.get(), 0.0001);
    }

    @Test
    public void inversionWorks(){
        rateLimitedController.setInverted(true);
        verify(speedController).setInverted(true);

        rateLimitedController.setInverted(false);
        verify(speedController).setInverted(false);

        when(speedController.getInverted()).thenReturn(false);
        assertFalse(rateLimitedController.getInverted());

        when(speedController.getInverted()).thenReturn(true);
        assertTrue(rateLimitedController.getInverted());
    }

    @Test
    public void disableWorks(){
        rateLimitedController.disable();
        verify(speedController).disable();
    }

    @Test
    public void stopWorks(){
        rateLimitedController.stopMotor();
        verify(speedController).stopMotor();
    }

    @Test
    public void pidWriteIgnoresRamp(){
        rateLimitedController.pidWrite(1.0);
        verify(speedController).pidWrite(1.0);
    }

    @Test
    public void adaptsToSpeedControllerBeingSetElsewhere(){
        when(speedController.get()).thenReturn(1.0);
        rateLimitedController.set(0.0);
        verify(speedController).set(0.9);
    }

    @Test(expected = GongolieriumException.class)
    public void throwsOnNullController(){
        new RateLimitedSpeedController(null, 0.1);
    }

    @Test(expected = GongolieriumException.class)
    public void throwsOnZeroMaxRate(){
        new RateLimitedSpeedController(null, 0.0);
    }

    @Test(expected = GongolieriumException.class)
    public void throwsOnNegativeMaxRate(){
        new RateLimitedSpeedController(null, -0.1);
    }

}