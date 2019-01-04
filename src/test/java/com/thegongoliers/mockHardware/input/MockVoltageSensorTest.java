package com.thegongoliers.mockHardware.input;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class MockVoltageSensorTest {
    private MockVoltageSensor voltageSensor;

    @Before
    public void setup(){
        voltageSensor = new MockVoltageSensor();
    }

    @Test
    public void test(){
        voltageSensor.setVoltage(0);

        assertEquals(0, voltageSensor.getVoltage(), 0);

        voltageSensor.setVoltage(1);

        assertEquals(1, voltageSensor.getVoltage(), 0);
    }
}