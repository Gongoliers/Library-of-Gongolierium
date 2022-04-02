package com.thegongoliers.output.actuators;

import com.thegongoliers.output.control.PIDController;
import org.junit.Before;
import org.junit.Test;
import org.mockito.AdditionalMatchers;

import edu.wpi.first.wpilibj.motorcontrol.MotorController;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

import com.thegongoliers.input.odometry.EncoderSensor;
import com.thegongoliers.input.time.Clock;

public class GSpeedControllerTest {

    private GSpeedController speedController;
    private MotorController mockSpeedController;
    private EncoderSensor mockEncoder;
    private Clock mockClock;

    @Before
    public void setup(){
        mockSpeedController = mock(MotorController.class);
        mockEncoder = mock(EncoderSensor.class);
        mockClock = mock(Clock.class);
        when(mockClock.getTime()).thenReturn(0.0);
        speedController = new GSpeedController(mockSpeedController, mockEncoder, new PIDController(0.1, 0, 0), new PIDController(0.2, 0, 0), mockClock);
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
        when(mockEncoder.getVelocity()).thenReturn(0.0);
        when(mockSpeedController.get()).thenReturn(0.0);
        speedController.setVelocity(1);

        verify(mockSpeedController, times(1)).set(AdditionalMatchers.eq(0.2, 0.001));

        when(mockEncoder.getVelocity()).thenReturn(0.5);
        when(mockSpeedController.get()).thenReturn(0.2);
        speedController.setVelocity(1);

        verify(mockSpeedController, times(1)).set(AdditionalMatchers.eq(0.3, 0.001));

        when(mockEncoder.getVelocity()).thenReturn(1.0);
        when(mockSpeedController.get()).thenReturn(0.3);
        speedController.setVelocity(1);

        verify(mockSpeedController, times(2)).set(AdditionalMatchers.eq(0.3, 0.001));

        when(mockEncoder.getVelocity()).thenReturn(1.5);
        when(mockSpeedController.get()).thenReturn(0.3);
        speedController.setVelocity(1);

        verify(mockSpeedController, times(2)).set(AdditionalMatchers.eq(0.2, 0.001));
    }

    @Test
    public void canGetVelocity(){
        speedController.getVelocity();
        verify(mockEncoder).getVelocity();
    }

    @Test
    public void canScale(){
        speedController.setScale(0.5);
        speedController.set(0.1);
        verify(mockSpeedController).set(0.05);
        when(mockSpeedController.get()).thenReturn(0.05);
        assertEquals(0.1, speedController.get(), 0.01);
    }

    @Test
    public void limitsAccelerationOnUnfixedDT(){
        speedController.setScale(1.0);
        speedController.setSecondsToFullSpeed(0.2);
        when(mockClock.getTime()).thenReturn(0.01);
        speedController.set(0.2);
        verify(mockSpeedController).set(AdditionalMatchers.eq(0.1, 0.01));
        when(mockSpeedController.get()).thenReturn(0.1);

         when(mockClock.getTime()).thenReturn(0.2);
         speedController.set(0.05);
         verify(mockSpeedController).set(AdditionalMatchers.eq(0.05, 0.001));
         when(mockSpeedController.get()).thenReturn(0.05);

         when(mockClock.getTime()).thenReturn(0.21);
         speedController.set(-0.05);
         verify(mockSpeedController).set(AdditionalMatchers.eq(0.0, 0.001));
    }

    @Test
    public void limitsAccelerationOnFixedDT(){
        speedController.setSecondsToFullSpeed(0.2);
        when(mockClock.getTime()).thenReturn(0.02);
        speedController.set(1);
        verify(mockSpeedController).set(AdditionalMatchers.eq(0.1, 0.001));
        when(mockSpeedController.get()).thenReturn(0.1);

        when(mockClock.getTime()).thenReturn(0.04);
        speedController.set(-1);
        verify(mockSpeedController).set(AdditionalMatchers.eq(0.0, 0.001));
        when(mockSpeedController.get()).thenReturn(0.0);

        when(mockClock.getTime()).thenReturn(0.06);
        speedController.set(-0.2);
        verify(mockSpeedController).set(AdditionalMatchers.eq(-0.1, 0.001));
    }

}