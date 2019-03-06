package com.thegongoliers.mockHardware.input;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class MockPotentiometerTest {

    private MockPotentiometer potentiometer;

    @Before
    public void setup(){
        potentiometer = new MockPotentiometer();
    }

    @Test
    public void test(){
        potentiometer.setAngle(0);
        assertEquals(0, potentiometer.get(), 0.0001);


        potentiometer.setAngle(90);
        assertEquals(90, potentiometer.get(), 0.0001);


        potentiometer.setAngle(-90);
        assertEquals(-90, potentiometer.get(), 0.0001);
    }

}
