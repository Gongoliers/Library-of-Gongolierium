package com.thegongoliers.input.vision;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Created by Kylec on 2/19/2017.
 */
public class LEDColorTests {
    @Test
    public void testGreenRange(){
        assertNotNull(LEDColor.GreenV2.getHue());
        assertNotNull(LEDColor.GreenV2.getValue());
        assertNotNull(LEDColor.GreenV2.getSaturation());
    }

}
