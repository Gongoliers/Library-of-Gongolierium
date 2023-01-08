package com.thegongoliers.input.voltage;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class LowVoltageSensorTest {

    @Test
    public void testIsTriggered(){
        VoltageSensor voltageSensor = mock(VoltageSensor.class);
        LowVoltageSensor lowVoltageSensor = new LowVoltageSensor(voltageSensor, 0);

        when(voltageSensor.getVoltage()).thenReturn(0.0);
        assertTrue(lowVoltageSensor.isTriggered());

        when(voltageSensor.getVoltage()).thenReturn(1.0);
        assertFalse(lowVoltageSensor.isTriggered());

        when(voltageSensor.getVoltage()).thenReturn(-1.0);
        assertTrue(lowVoltageSensor.isTriggered());
    }
}