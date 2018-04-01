package com.thegongoliers.mockHardware.input;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class MockGyroTest {

    private MockGyro gyro;

    @Before
    public void setup(){
        gyro = new MockGyro();
    }

    @Test
    public void test(){
        gyro.setAngle(90);
        assertEquals(90, gyro.getAngle(), 0);

        gyro.reset();
        assertEquals(0, gyro.getAngle(), 0);

        gyro.setRate(0.3);
        assertEquals(0.3, gyro.getRate(), 0);
    }

}
