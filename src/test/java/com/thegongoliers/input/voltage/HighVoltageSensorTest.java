package com.thegongoliers.input.voltage;

import org.junit.Test;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class HighVoltageSensorTest {

    @Test
    public void testIsTriggered() {
        IVoltageSensor voltageSensor = mock(IVoltageSensor.class);
        HighVoltageSensor highVoltageSensor = new HighVoltageSensor(voltageSensor, 0);

        when(voltageSensor.getVoltage()).thenReturn(0.0);
        assertTrue(highVoltageSensor.isTriggered());

        when(voltageSensor.getVoltage()).thenReturn(1.0);
        assertTrue(highVoltageSensor.isTriggered());

        when(voltageSensor.getVoltage()).thenReturn(-1.0);
        assertFalse(highVoltageSensor.isTriggered());
    }
}