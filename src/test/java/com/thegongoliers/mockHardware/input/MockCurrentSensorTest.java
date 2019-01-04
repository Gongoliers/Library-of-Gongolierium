package com.thegongoliers.mockHardware.input;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class MockCurrentSensorTest {

    private MockCurrentSensor currentSensor;

    @Before
    public void setup(){
        currentSensor = new MockCurrentSensor();
    }

    @Test
    public void test(){
        currentSensor.setCurrent(0);

        assertEquals(0, currentSensor.getCurrent(), 0);

        currentSensor.setCurrent(1);

        assertEquals(1, currentSensor.getCurrent(), 0);
    }

}