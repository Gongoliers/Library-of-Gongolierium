package com.thegongoliers.mockHardware.input;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class MockClockTest {

    private MockClock mockClock;

    @Before
    public void setup(){
        mockClock = new MockClock();
    }

    @Test
    public void test(){
        mockClock.setTime(0);

        assertEquals(0, mockClock.getTime(), 0);

        mockClock.setTime(1);

        assertEquals(1, mockClock.getTime(), 0);


    }

}