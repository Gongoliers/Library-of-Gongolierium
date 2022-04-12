package com.thegongoliers.output.drivetrain;

import org.junit.Before;
import org.junit.Test;

import static org.mockito.Mockito.*;

import com.thegongoliers.input.odometry.EncoderSensor;
import com.thegongoliers.input.time.Clock;
import com.thegongoliers.output.interfaces.Drivetrain;

/**
 * TractionControlModuleTest
 */
public class EncoderStabilityModuleTest {

    private Drivetrain drivetrain;
    private ModularDrivetrain modularDrivetrain;
    private DriveModule module;
    private EncoderSensor encoder1, encoder2;

    @Before
    public void setup(){
        drivetrain = mock(Drivetrain.class);
        modularDrivetrain = new ModularDrivetrain(drivetrain, mock(Clock.class));
        encoder1 = mock(EncoderSensor.class);
        encoder2 = mock(EncoderSensor.class);
        module = new EncoderStabilityModule(encoder1, encoder2, 0.1, 0.1);
        modularDrivetrain.addModule(module);
    }

    @Test
    public void doesNotApplyWhileTurning(){
        when(encoder1.getVelocity()).thenReturn(1.0);
        when(encoder2.getVelocity()).thenReturn(0.5);
        modularDrivetrain.arcade(1, 0.5);
        DrivetrainTestUtils.verifyArcade(drivetrain, 1, 0.5);

        when(encoder1.getVelocity()).thenReturn(1.0);
        when(encoder2.getVelocity()).thenReturn(-0.5);
        modularDrivetrain.tank(1, -0.5);
        DrivetrainTestUtils.verifyTank(drivetrain, 1, -0.5);
    }

    @Test
    public void appliesWhileNotTurning(){
        // Left side slipping
        when(encoder1.getVelocity()).thenReturn(2.0);
        when(encoder2.getVelocity()).thenReturn(1.0);
        modularDrivetrain.tank(1, 1);
        DrivetrainTestUtils.verifyTank(drivetrain, 0.9, 1);

        // Right side slipping
        when(encoder1.getVelocity()).thenReturn(1.0);
        when(encoder2.getVelocity()).thenReturn(3.0);
        modularDrivetrain.tank(1, 1);
        DrivetrainTestUtils.verifyTank(drivetrain, 1, 0.8);
    }

}