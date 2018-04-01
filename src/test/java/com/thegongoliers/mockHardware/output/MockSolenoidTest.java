package com.thegongoliers.mockHardware.output;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class MockSolenoidTest {

    private MockSolenoid solenoid;

    @Before
    public void setup(){
        solenoid = new MockSolenoid();
    }

    @Test
    public void test(){
        solenoid.set(true);
        assertTrue(solenoid.get());

        solenoid.set(false);
        assertFalse(solenoid.get());
    }

}