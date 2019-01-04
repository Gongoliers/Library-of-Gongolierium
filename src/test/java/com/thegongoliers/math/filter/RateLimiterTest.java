package com.thegongoliers.math.filter;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class RateLimiterTest {

    private RateLimiter rateLimiter;

    @Before
    public void setup(){
        rateLimiter = new RateLimiter(0.1);
    }

    @Test
    public void testRateLimiting(){
        assertEquals(0, rateLimiter.filter(0), 0.0001);
        assertEquals(0.1, rateLimiter.filter(1), 0.0001);
        assertEquals(0.2, rateLimiter.filter(1), 0.0001);
        assertEquals(0.3, rateLimiter.filter(1), 0.0001);
        assertEquals(0.305, rateLimiter.filter(0.305), 0.0001);
        assertEquals(0.3, rateLimiter.filter(0.3), 0.0001);
        assertEquals(0.2, rateLimiter.filter(-1), 0.0001);
    }

}