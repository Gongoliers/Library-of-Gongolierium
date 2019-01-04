package com.thegongoliers.input.voltage;

import com.thegongoliers.mockHardware.input.MockVoltageSensor;
import org.junit.Test;

import static org.junit.Assert.*;

public class LowVoltageSensorTest {

    @Test
    public void testIsTriggered(){
        MockVoltageSensor voltageSensor = new MockVoltageSensor();
        LowVoltageSensor lowVoltageSensor = new LowVoltageSensor(voltageSensor, 0);

        voltageSensor.setVoltage(0);
        assertTrue(lowVoltageSensor.isTriggered());

        voltageSensor.setVoltage(1);
        assertFalse(lowVoltageSensor.isTriggered());

        voltageSensor.setVoltage(-1);
        assertTrue(lowVoltageSensor.isTriggered());
    }
}