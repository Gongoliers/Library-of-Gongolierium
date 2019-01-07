package com.thegongoliers.pathFollowing.controllers;

import com.thegongoliers.mockHardware.input.MockClock;
import com.thegongoliers.pathFollowing.controllers.MotionProfileController;
import org.junit.Test;

import static org.junit.Assert.*;

public class MotionProfileControllerTest {

    @Test
    public void test(){

        MockClock mockClock = new MockClock();

        MotionProfileController controller = new MotionProfileController(0.01, 0.0001, 0.01, 0.1, 0.02, 0.1, mockClock);

        assertEquals(controller.calculate(0, 1, 8, 4), 0.8901, 0.00001);
        mockClock.setTime(1);
        assertEquals(controller.calculate(1, 2, 6, -2), 0.5101, 0.00001);

        assertTrue(controller.isOnTarget(1, 1.01));
        assertFalse(controller.isOnTarget(0.9, 1.01));
    }

}