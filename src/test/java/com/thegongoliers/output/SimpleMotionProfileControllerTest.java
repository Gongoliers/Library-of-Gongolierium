package com.thegongoliers.output;

import com.thegongoliers.input.time.MockClock;
import com.thegongoliers.pathFollowing.SimpleMotionProfileController;
import org.junit.Test;

import static org.junit.Assert.*;

public class SimpleMotionProfileControllerTest {

    @Test
    public void test(){

        MockClock mockClock = new MockClock();

        SimpleMotionProfileController controller = new SimpleMotionProfileController(0.01, 0.0001, 0.01, 0.1, 0.02, mockClock);

        assertEquals(controller.calculate(0, 1, 8, 4), 0.8901, 0.00001);
        mockClock.setTime(1);
        assertEquals(controller.calculate(1, 2, 6, -2), 0.5101, 0.00001);
    }

}