package com.thegongoliers.input.odometry;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class AverageEncoderSensorTest {
    
    @Test
    public void getDistance(){
        var encoder1 = mock(EncoderSensor.class);
        var encoder2 = mock(EncoderSensor.class);
        var encoder3 = mock(EncoderSensor.class);

        when(encoder1.getDistance()).thenReturn(10.0);
        when(encoder2.getDistance()).thenReturn(12.0);
        when(encoder3.getDistance()).thenReturn(17.0);

        var average = new AverageEncoderSensor(encoder1, encoder2, encoder3);

        var actual = average.getDistance();

        assertEquals(13.0, actual, 0.001);
    }

    @Test
    public void getVelocity(){
        var encoder1 = mock(EncoderSensor.class);
        var encoder2 = mock(EncoderSensor.class);
        var encoder3 = mock(EncoderSensor.class);

        when(encoder1.getVelocity()).thenReturn(10.0);
        when(encoder2.getVelocity()).thenReturn(12.0);
        when(encoder3.getVelocity()).thenReturn(17.0);

        var average = new AverageEncoderSensor(encoder1, encoder2, encoder3);

        var actual = average.getVelocity();

        assertEquals(13.0, actual, 0.001);
    }

    @Test
    public void reset(){
        var encoder1 = mock(EncoderSensor.class);
        var encoder2 = mock(EncoderSensor.class);
        var encoder3 = mock(EncoderSensor.class);

        var average = new AverageEncoderSensor(encoder1, encoder2, encoder3);

        average.reset();

        verify(encoder1, times(1)).reset();
        verify(encoder2, times(1)).reset();
        verify(encoder3, times(1)).reset();
    }

}
