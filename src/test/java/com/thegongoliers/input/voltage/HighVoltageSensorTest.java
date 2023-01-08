package com.thegongoliers.input.voltage;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class HighVoltageSensorTest {

    @Test
    public void testIsTriggered() {
        VoltageSensor voltageSensor = mock(VoltageSensor.class);
        HighVoltageSensor highVoltageSensor = new HighVoltageSensor(voltageSensor, 0);

        when(voltageSensor.getVoltage()).thenReturn(0.0);
        assertTrue(highVoltageSensor.isTriggered());

        when(voltageSensor.getVoltage()).thenReturn(1.0);
        assertTrue(highVoltageSensor.isTriggered());

        when(voltageSensor.getVoltage()).thenReturn(-1.0);
        assertFalse(highVoltageSensor.isTriggered());
    }
}