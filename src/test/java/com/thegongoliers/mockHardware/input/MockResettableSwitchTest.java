package com.thegongoliers.mockHardware.input;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class MockResettableSwitchTest {

    private MockResettableSwitch mockSwitch;

    @Before
    public void setup(){
        mockSwitch = new MockResettableSwitch();
    }

    @Test
    public void test(){
        mockSwitch.setTriggered(true);
        assertTrue(mockSwitch.isTriggered());

        mockSwitch.setTriggered(false);
        assertFalse(mockSwitch.isTriggered());

        mockSwitch.setTriggered(true);
        mockSwitch.reset();
        assertFalse(mockSwitch.isTriggered());
    }

}