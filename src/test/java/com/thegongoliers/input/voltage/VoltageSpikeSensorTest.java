package com.thegongoliers.input.voltage;

import com.thegongoliers.mockHardware.input.MockVoltageSensor;
import org.junit.Test;

import static org.junit.Assert.*;

public class VoltageSpikeSensorTest {

    @Test
    public void testIsTriggered() {
        MockVoltageSensor voltageSensor = new MockVoltageSensor();
        HighVoltageSensor highVoltageSensor = new HighVoltageSensor(voltageSensor, 0);
        VoltageSpikeSensor voltageSpikeSensor = new VoltageSpikeSensor(highVoltageSensor);

        voltageSensor.setVoltage(-1);
        assertFalse(voltageSpikeSensor.isTriggered());

        voltageSensor.setVoltage(1);
        assertFalse(voltageSpikeSensor.isTriggered());

        voltageSensor.setVoltage(-1);
        assertTrue(voltageSpikeSensor.isTriggered());

        voltageSensor.setVoltage(-1);
        assertTrue(voltageSpikeSensor.isTriggered());

        voltageSensor.setVoltage(1);
        assertFalse(voltageSpikeSensor.isTriggered());
    }

    @Test
    public void testReset() {
        MockVoltageSensor voltageSensor = new MockVoltageSensor();
        HighVoltageSensor highVoltageSensor = new HighVoltageSensor(voltageSensor, 0);
        VoltageSpikeSensor voltageSpikeSensor = new VoltageSpikeSensor(highVoltageSensor);

        voltageSensor.setVoltage(-1);
        assertFalse(voltageSpikeSensor.isTriggered());

        voltageSensor.setVoltage(1);
        assertFalse(voltageSpikeSensor.isTriggered());

        voltageSensor.setVoltage(-1);
        assertTrue(voltageSpikeSensor.isTriggered());

        voltageSpikeSensor.reset();

        voltageSensor.setVoltage(-1);
        assertFalse(voltageSpikeSensor.isTriggered());
    }
}