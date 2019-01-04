package com.thegongoliers.mockHardware.input;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class MockEncoderTest {

    private MockEncoder encoder;

    @Before
    public void setup(){
        encoder = new MockEncoder();
    }

    @Test
    public void test(){
        encoder.setDistancePerPulse(2);

        encoder.setPulses(10);

        assertEquals(10, encoder.getPulses(), 0);
        assertEquals(20, encoder.getDistance(), 0);

        encoder.setVelocity(5);
        assertEquals(5, encoder.getVelocity(), 0);


        encoder.setDistancePerPulse(3);
        encoder.setInverted(true);
        assertEquals(10, encoder.getPulses(), 0);
        assertEquals(30, encoder.getDistance(), 0);
        assertEquals(5, encoder.getVelocity(), 0);

        assertTrue(encoder.isInverted());

        encoder.reset();
        assertEquals(0, encoder.getPulses(), 0);
        assertEquals(0, encoder.getDistance(), 0);
        assertEquals(0, encoder.getVelocity(), 0);



    }

}