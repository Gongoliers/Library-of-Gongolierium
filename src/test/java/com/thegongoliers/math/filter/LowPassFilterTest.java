package com.thegongoliers.math.filter;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class LowPassFilterTest {

    private LowPassFilter lowPassFilter;

    @Before
    public void setup(){
        lowPassFilter = new LowPassFilter(0.1);
    }

    @Test
    public void testLowPassFilter(){
        assertEquals(0, lowPassFilter.filter(0), 0.0001);
        assertEquals(0.9, lowPassFilter.filter(1), 0.0001);
        assertEquals(0.99, lowPassFilter.filter(1), 0.0001);
        assertEquals(-0.801, lowPassFilter.filter(-1), 0.0001);
        assertEquals(-0.9801, lowPassFilter.filter(-1), 0.0001);

    }

}