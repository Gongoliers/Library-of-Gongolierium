package com.thegongoliers.mockHardware.output;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class MockSpeedControllerTest {

    private MockSpeedController mockSpeedController;

    @Before
    public void setup(){
        mockSpeedController = new MockSpeedController();
    }

    @Test
    public void test(){
        mockSpeedController.set(1);
        assertEquals(1, mockSpeedController.get(), 0);

        mockSpeedController.set(-1);
        assertEquals(-1, mockSpeedController.get(), 0);

        mockSpeedController.disable();
        assertEquals(0, mockSpeedController.get(), 0);

        mockSpeedController.pidWrite(1);
        assertEquals(1, mockSpeedController.get(), 0);

        mockSpeedController.stopMotor();
        assertEquals(0, mockSpeedController.get(), 0);

        mockSpeedController.setInverted(true);
        assertTrue(mockSpeedController.getInverted());

        mockSpeedController.set(1);
        assertEquals(1, mockSpeedController.get(), 0);
    }

}