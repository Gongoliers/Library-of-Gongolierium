package com.thegongoliers.input.current;

import com.thegongoliers.mockHardware.input.MockCurrentSensor;
import org.junit.Test;

import static org.junit.Assert.*;

public class LowCurrentSensorTest {

    @Test
    public void testIsTriggered() {
        MockCurrentSensor currentSensor = new MockCurrentSensor();
        LowCurrentSensor lowCurrentSensor = new LowCurrentSensor(currentSensor, 0);

        currentSensor.setCurrent(0);
        assertTrue(lowCurrentSensor.isTriggered());

        currentSensor.setCurrent(1);
        assertFalse(lowCurrentSensor.isTriggered());

        currentSensor.setCurrent(-1);
        assertTrue(lowCurrentSensor.isTriggered());
    }

}