package com.thegongoliers.input.current;

import org.junit.Test;

import static org.junit.Assert.*;

import static org.mockito.Mockito.*;

public class CurrentSpikeSensorTest {

    @Test
    public void testIsTriggered() {
        CurrentSensor currentSensor = mock(CurrentSensor.class);
        HighCurrentSensor highCurrentSensor = new HighCurrentSensor(currentSensor, 0);
        CurrentSpikeSensor currentSpikeSensor = new CurrentSpikeSensor(highCurrentSensor);

        when(currentSensor.getCurrent()).thenReturn(-1.0);
        assertFalse(currentSpikeSensor.isTriggered());

        when(currentSensor.getCurrent()).thenReturn(1.0);
        assertFalse(currentSpikeSensor.isTriggered());

        when(currentSensor.getCurrent()).thenReturn(-1.0);
        assertTrue(currentSpikeSensor.isTriggered());

        when(currentSensor.getCurrent()).thenReturn(-1.0);
        assertTrue(currentSpikeSensor.isTriggered());

        when(currentSensor.getCurrent()).thenReturn(1.0);
        assertFalse(currentSpikeSensor.isTriggered());
    }

    @Test
    public void testReset() {
        CurrentSensor currentSensor = mock(CurrentSensor.class);
        HighCurrentSensor highCurrentSensor = new HighCurrentSensor(currentSensor, 0);
        CurrentSpikeSensor currentSpikeSensor = new CurrentSpikeSensor(highCurrentSensor);

        when(currentSensor.getCurrent()).thenReturn(-1.0);
        assertFalse(currentSpikeSensor.isTriggered());

        when(currentSensor.getCurrent()).thenReturn(1.0);
        assertFalse(currentSpikeSensor.isTriggered());

        when(currentSensor.getCurrent()).thenReturn(-1.0);
        assertTrue(currentSpikeSensor.isTriggered());

        currentSpikeSensor.reset();

        when(currentSensor.getCurrent()).thenReturn(-1.0);
        assertFalse(currentSpikeSensor.isTriggered());
    }

}