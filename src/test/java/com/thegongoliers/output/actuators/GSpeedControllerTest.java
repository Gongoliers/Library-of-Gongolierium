package com.thegongoliers.output.actuators;

import org.junit.Before;
import org.junit.Test;
import org.mockito.AdditionalMatchers;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.SpeedController;

import static org.mockito.Mockito.*;

public class GSpeedControllerTest {

    private GSpeedController speedController;
    private SpeedController mockSpeedController;
    private Encoder mockEncoder;

    @Before
    public void setup(){
        mockSpeedController = mock(SpeedController.class);
        mockEncoder = mock(Encoder.class);
        speedController = new GSpeedController(mockSpeedController, mockEncoder, 0.1, 0.2);
    }

    @Test
    public void canDoNormalSpeedControllerFunctions(){
        speedController.set(0.1);
        verify(mockSpeedController).set(0.1);

        speedController.get();
        verify(mockSpeedController).get();

        speedController.stopMotor();
        verify(mockSpeedController).stopMotor();

        speedController.getInverted();
        verify(mockSpeedController).getInverted();

        speedController.setInverted(true);
        verify(mockSpeedController).setInverted(true);

        speedController.pidWrite(1);
        verify(mockSpeedController).pidWrite(1);
    }

    @Test
    public void canSetDistance(){
        when(mockEncoder.getDistance()).thenReturn(0.0);
        speedController.setDistance(1);

        verify(mockSpeedController).set(0.1);

        when(mockEncoder.getDistance()).thenReturn(0.5);
        speedController.setDistance(1);

        verify(mockSpeedController).set(0.05);

        when(mockEncoder.getDistance()).thenReturn(1.0);
        speedController.setDistance(1);

        verify(mockSpeedController).set(0.0);
    }

    @Test
    public void canGetDistance(){
        speedController.getDistance();
        verify(mockEncoder).getDistance();
    }

    @Test
    public void canSetVelocity(){
        when(mockEncoder.getRate()).thenReturn(0.0);
        when(mockSpeedController.get()).thenReturn(0.0);
        speedController.setVelocity(1);

        verify(mockSpeedController, times(1)).set(AdditionalMatchers.eq(0.2, 0.001));

        when(mockEncoder.getRate()).thenReturn(0.5);
        when(mockSpeedController.get()).thenReturn(0.2);
        speedController.setVelocity(1);

        verify(mockSpeedController, times(1)).set(AdditionalMatchers.eq(0.3, 0.001));

        when(mockEncoder.getRate()).thenReturn(1.0);
        when(mockSpeedController.get()).thenReturn(0.3);
        speedController.setVelocity(1);

        verify(mockSpeedController, times(2)).set(AdditionalMatchers.eq(0.3, 0.001));

        when(mockEncoder.getRate()).thenReturn(1.5);
        when(mockSpeedController.get()).thenReturn(0.3);
        speedController.setVelocity(1);

        verify(mockSpeedController, times(2)).set(AdditionalMatchers.eq(0.2, 0.001));
    }

    @Test
    public void canGetVelocity(){
        speedController.getVelocity();
        verify(mockEncoder).getRate();
    }
}