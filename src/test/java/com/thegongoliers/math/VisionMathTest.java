package com.thegongoliers.math;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class VisionMathTest {

    @Test
    public void testDistance(){
        var actual = VisionMath.getDistance(25, 20, 10, 60);
        assertEquals(57.1259, actual, 0.01);
    }
    
}
