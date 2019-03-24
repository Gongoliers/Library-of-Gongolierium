package com.thegongoliers.input.current;

import org.junit.Test;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class LowCurrentSensorTest {

    @Test
    public void testIsTriggered() {
        CurrentSensor currentSensor = mock(CurrentSensor.class);
        LowCurrentSensor lowCurrentSensor = new LowCurrentSensor(currentSensor, 0);

        when(currentSensor.getCurrent()).thenReturn(0.0);
        assertTrue(lowCurrentSensor.isTriggered());

        when(currentSensor.getCurrent()).thenReturn(1.0);
        assertFalse(lowCurrentSensor.isTriggered());

        when(currentSensor.getCurrent()).thenReturn(-1.0);
        assertTrue(lowCurrentSensor.isTriggered());
    }

}