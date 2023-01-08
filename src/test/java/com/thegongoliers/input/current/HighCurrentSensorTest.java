package com.thegongoliers.input.current;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import static org.mockito.Mockito.*;

public class HighCurrentSensorTest {

    @Test
    public void testIsTriggered() {
        CurrentSensor currentSensor = mock(CurrentSensor.class);
        HighCurrentSensor highCurrentSensor = new HighCurrentSensor(currentSensor, 0);

        when(currentSensor.getCurrent()).thenReturn(0.0);
        assertTrue(highCurrentSensor.isTriggered());

        when(currentSensor.getCurrent()).thenReturn(1.0);
        assertTrue(highCurrentSensor.isTriggered());

        when(currentSensor.getCurrent()).thenReturn(-1.0);
        assertFalse(highCurrentSensor.isTriggered());
    }

}