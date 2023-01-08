package com.thegongoliers.input.odometry;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.thegongoliers.utils.Resettable;

public class BaseEncoderSensorTest {
    
    @Test
    public void getDistance(){
        var encoder = new BaseEncoderSensor(() -> 10.0, () -> 20.0);
        assertEquals(10.0, encoder.getDistance(), 0.0001);
    }

    @Test
    public void getVelocity(){
        var encoder = new BaseEncoderSensor(() -> 10.0, () -> 20.0);
        assertEquals(20.0, encoder.getVelocity(), 0.0001);
    }

    @Test
    public void resetNoResettable(){
        var distance = mock(DistanceSensor.class);
        when(distance.getDistance()).thenReturn(10.0);

        var encoder = new BaseEncoderSensor(distance, () -> 20.0);

        assertEquals(10.0, encoder.getDistance(), 0.0001);
        assertEquals(20.0, encoder.getVelocity(), 0.0001);
        encoder.reset();
        assertEquals(0.0, encoder.getDistance(), 0.0001);
        when(distance.getDistance()).thenReturn(12.0);
        assertEquals(2.0, encoder.getDistance(), 0.0001);
        assertEquals(20.0, encoder.getVelocity(), 0.0001);
    }

    @Test
    public void resetResettable(){
        var distance = mock(DistanceSensor.class);
        var resettable = mock(Resettable.class);

        when(distance.getDistance()).thenReturn(10.0);

        var encoder = new BaseEncoderSensor(distance, () -> 20.0, resettable);

        assertEquals(10.0, encoder.getDistance(), 0.0001);
        assertEquals(20.0, encoder.getVelocity(), 0.0001);
        verify(resettable, times(0)).reset();
        encoder.reset();
        verify(resettable, times(1)).reset();
        assertEquals(10.0, encoder.getDistance(), 0.0001);
        assertEquals(20.0, encoder.getVelocity(), 0.0001);
    }

    @Test
    public void inverted(){
        var encoder = new BaseEncoderSensor(() -> 10.0, () -> 20.0).inverted();
        assertEquals(-10.0, encoder.getDistance(), 0.0001);
        assertEquals(-20.0, encoder.getVelocity(), 0.0001);
    }

    @Test
    public void scaled(){
        var encoder = new BaseEncoderSensor(() -> 10.0, () -> 20.0).scaledBy(2.0);
        assertEquals(20.0, encoder.getDistance(), 0.0001);
        assertEquals(40.0, encoder.getVelocity(), 0.0001);
    }

    @Test
    public void invertedResetNoResettable(){
        var distance = mock(DistanceSensor.class);
        when(distance.getDistance()).thenReturn(10.0);

        var encoder = new BaseEncoderSensor(distance, () -> 20.0).inverted();
        assertEquals(-10.0, encoder.getDistance(), 0.0001);
        assertEquals(-20.0, encoder.getVelocity(), 0.0001);

        encoder.reset();

        when(distance.getDistance()).thenReturn(12.0);

        assertEquals(-2.0, encoder.getDistance(), 0.0001);
        assertEquals(-20.0, encoder.getVelocity(), 0.0001);
    }

    @Test
    public void scaledResetNoResettable(){
        var distance = mock(DistanceSensor.class);
        when(distance.getDistance()).thenReturn(10.0);

        var encoder = new BaseEncoderSensor(distance, () -> 20.0).scaledBy(2.0);
        assertEquals(20.0, encoder.getDistance(), 0.0001);
        assertEquals(40.0, encoder.getVelocity(), 0.0001);

        encoder.reset();

        when(distance.getDistance()).thenReturn(12.0);

        assertEquals(4.0, encoder.getDistance(), 0.0001);
        assertEquals(40.0, encoder.getVelocity(), 0.0001);
    }

    @Test
    public void invertedResetResettable(){
        var distance = mock(DistanceSensor.class);
        when(distance.getDistance()).thenReturn(10.0);

        var reset = mock(Resettable.class);

        var encoder = new BaseEncoderSensor(distance, () -> 20.0, reset).inverted();
        assertEquals(-10.0, encoder.getDistance(), 0.0001);
        assertEquals(-20.0, encoder.getVelocity(), 0.0001);

        encoder.reset();

        when(distance.getDistance()).thenReturn(12.0);
        verify(reset, times(1)).reset();
        assertEquals(-12.0, encoder.getDistance(), 0.0001);
        assertEquals(-20.0, encoder.getVelocity(), 0.0001);
    }

    @Test
    public void scaledResetResettable(){
        var distance = mock(DistanceSensor.class);
        when(distance.getDistance()).thenReturn(10.0);

        var reset = mock(Resettable.class);

        var encoder = new BaseEncoderSensor(distance, () -> 20.0, reset).scaledBy(2.0);
        assertEquals(20.0, encoder.getDistance(), 0.0001);
        assertEquals(40.0, encoder.getVelocity(), 0.0001);

        encoder.reset();

        when(distance.getDistance()).thenReturn(12.0);
        verify(reset, times(1)).reset();
        assertEquals(24.0, encoder.getDistance(), 0.0001);
        assertEquals(40.0, encoder.getVelocity(), 0.0001);
    }

}
