package com.thegongoliers.math;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class VisionMathTest {

    @Test
    public void testDistance(){
        var actual = VisionMath.getDistance(25, 20, 10, 60);
        assertEquals(57.1259, actual, 0.01);
    }
    
}
