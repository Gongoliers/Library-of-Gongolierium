package com.thegongoliers.math;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by Kyle on 7/9/2017.
 */
public class ExponentialMovingAverageTest {
    @Test
    public void calculate() throws Exception {
        ExponentialMovingAverage ema = new ExponentialMovingAverage(10);
        assertEquals(22.27, ema.calculate(22.27), 0.0);
        ema.calculate(22.19);
        ema.calculate(22.08);
        ema.calculate(22.17);
        ema.calculate(22.18);
        ema.calculate(22.13);
        ema.calculate(22.23);
        ema.calculate(22.43);
        assertEquals(22.24, ema.calculate(22.24), 0.0);
        assertEquals(22.22, ema.calculate(22.29), 0.005);
        assertEquals(22.21, ema.calculate(22.15), 0.005);

    }

}