package com.thegongoliers.input.voltage;

import com.thegongoliers.mockHardware.input.MockVoltageSensor;
import org.junit.Test;

import static org.junit.Assert.*;

public class HighVoltageSensorTest {

    @Test
    public void testIsTriggered() {
        MockVoltageSensor voltageSensor = new MockVoltageSensor();
        HighVoltageSensor highVoltageSensor = new HighVoltageSensor(voltageSensor, 0);

        voltageSensor.setVoltage(0);
        assertTrue(highVoltageSensor.isTriggered());

        voltageSensor.setVoltage(1);
        assertTrue(highVoltageSensor.isTriggered());

        voltageSensor.setVoltage(-1);
        assertFalse(highVoltageSensor.isTriggered());
    }
}