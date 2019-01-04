package com.thegongoliers.input.current;

import com.thegongoliers.mockHardware.input.MockCurrentSensor;
import org.junit.Test;

import static org.junit.Assert.*;

public class HighCurrentSensorTest {

    @Test
    public void testIsTriggered() {
        MockCurrentSensor currentSensor = new MockCurrentSensor();
        HighCurrentSensor highCurrentSensor = new HighCurrentSensor(currentSensor, 0);

        currentSensor.setCurrent(0);
        assertTrue(highCurrentSensor.isTriggered());

        currentSensor.setCurrent(1);
        assertTrue(highCurrentSensor.isTriggered());

        currentSensor.setCurrent(-1);
        assertFalse(highCurrentSensor.isTriggered());
    }

}