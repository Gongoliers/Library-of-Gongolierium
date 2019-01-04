package com.thegongoliers.input.current;

import com.thegongoliers.mockHardware.input.MockCurrentSensor;
import org.junit.Test;

import static org.junit.Assert.*;

public class CurrentSpikeSensorTest {

    @Test
    public void testIsTriggered() {
        MockCurrentSensor currentSensor = new MockCurrentSensor();
        HighCurrentSensor highCurrentSensor = new HighCurrentSensor(currentSensor, 0);
        CurrentSpikeSensor currentSpikeSensor = new CurrentSpikeSensor(highCurrentSensor);

        currentSensor.setCurrent(-1);
        assertFalse(currentSpikeSensor.isTriggered());

        currentSensor.setCurrent(1);
        assertFalse(currentSpikeSensor.isTriggered());

        currentSensor.setCurrent(-1);
        assertTrue(currentSpikeSensor.isTriggered());

        currentSensor.setCurrent(-1);
        assertTrue(currentSpikeSensor.isTriggered());

        currentSensor.setCurrent(1);
        assertFalse(currentSpikeSensor.isTriggered());
    }

    @Test
    public void testReset() {
        MockCurrentSensor currentSensor = new MockCurrentSensor();
        HighCurrentSensor highCurrentSensor = new HighCurrentSensor(currentSensor, 0);
        CurrentSpikeSensor currentSpikeSensor = new CurrentSpikeSensor(highCurrentSensor);

        currentSensor.setCurrent(-1);
        assertFalse(currentSpikeSensor.isTriggered());

        currentSensor.setCurrent(1);
        assertFalse(currentSpikeSensor.isTriggered());

        currentSensor.setCurrent(-1);
        assertTrue(currentSpikeSensor.isTriggered());

        currentSpikeSensor.reset();

        currentSensor.setCurrent(-1);
        assertFalse(currentSpikeSensor.isTriggered());
    }

}