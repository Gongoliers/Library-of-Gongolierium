package com.thegongoliers.input.odometry;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import edu.wpi.first.wpilibj.Encoder;
import static org.mockito.Mockito.*;

public class EncoderUtilsTest {

    @Test
    public void toDistanceSensor() {
        Encoder encoder = mock(Encoder.class);
        DistanceSensor distanceSensor = EncoderUtils.toDistanceSensor(encoder);

        when(encoder.getDistance()).thenReturn(0.0);
        assertEquals(0.0, distanceSensor.getDistance(), 0.0001);

        when(encoder.getDistance()).thenReturn(1.0);
        assertEquals(1.0, distanceSensor.getDistance(), 0.0001);

    }

    @Test
    public void toVelocitySensor() {
        Encoder encoder = mock(Encoder.class);
        VelocitySensor velocitySensor = EncoderUtils.toVelocitySensor(encoder);

        when(encoder.getRate()).thenReturn(0.0);
        assertEquals(0.0, velocitySensor.getVelocity(), 0.0001);

        when(encoder.getRate()).thenReturn(1.0);
        assertEquals(1.0, velocitySensor.getVelocity(), 0.0001);
    }
}