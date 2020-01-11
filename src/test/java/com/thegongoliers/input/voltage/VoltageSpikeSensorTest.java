package com.thegongoliers.input.voltage;

import org.junit.Test;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class VoltageSpikeSensorTest {

    @Test
    public void testIsTriggered() {
        VoltageSensor voltageSensor = mock(VoltageSensor.class);
        HighVoltageSensor highVoltageSensor = new HighVoltageSensor(voltageSensor, 0);
        VoltageSpikeSensor voltageSpikeSensor = new VoltageSpikeSensor(highVoltageSensor);

        when(voltageSensor.getVoltage()).thenReturn(-1.0);
        assertFalse(voltageSpikeSensor.isTriggered());

        when(voltageSensor.getVoltage()).thenReturn(1.0);
        assertFalse(voltageSpikeSensor.isTriggered());

        when(voltageSensor.getVoltage()).thenReturn(-1.0);
        assertTrue(voltageSpikeSensor.isTriggered());

        when(voltageSensor.getVoltage()).thenReturn(-1.0);
        assertTrue(voltageSpikeSensor.isTriggered());

        when(voltageSensor.getVoltage()).thenReturn(1.0);
        assertFalse(voltageSpikeSensor.isTriggered());
    }

    @Test
    public void testReset() {
        VoltageSensor voltageSensor = mock(VoltageSensor.class);
        HighVoltageSensor highVoltageSensor = new HighVoltageSensor(voltageSensor, 0);
        VoltageSpikeSensor voltageSpikeSensor = new VoltageSpikeSensor(highVoltageSensor);

        when(voltageSensor.getVoltage()).thenReturn(-1.0);
        assertFalse(voltageSpikeSensor.isTriggered());

        when(voltageSensor.getVoltage()).thenReturn(1.0);
        assertFalse(voltageSpikeSensor.isTriggered());

        when(voltageSensor.getVoltage()).thenReturn(-1.0);
        assertTrue(voltageSpikeSensor.isTriggered());

        voltageSpikeSensor.reset();

        when(voltageSensor.getVoltage()).thenReturn(-1.0);
        assertFalse(voltageSpikeSensor.isTriggered());
    }
}