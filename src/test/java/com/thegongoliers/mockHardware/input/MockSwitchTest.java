package com.thegongoliers.mockHardware.input;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class MockSwitchTest {

    private MockSwitch mockSwitch;

    @Before
    public void setup(){
        mockSwitch = new MockSwitch();
    }

    @Test
    public void test(){
        mockSwitch.setTriggered(true);
        assertTrue(mockSwitch.isTriggered());

        mockSwitch.setTriggered(false);
        assertFalse(mockSwitch.isTriggered());
    }

}