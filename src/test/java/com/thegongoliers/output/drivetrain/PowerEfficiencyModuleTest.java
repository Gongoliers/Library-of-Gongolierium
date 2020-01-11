package com.thegongoliers.output.drivetrain;

import org.junit.Before;
import org.junit.Test;

import static org.mockito.Mockito.*;

import com.thegongoliers.input.time.Clock;
import com.thegongoliers.output.interfaces.Drivetrain;

/**
 * PowerEfficiencyModuleTest
 */
public class PowerEfficiencyModuleTest {

    private Drivetrain drivetrain;
    private ModularDrivetrain modularDrivetrain;
    private PowerEfficiencyModule module;
    private Clock clock;

    @Before
    public void setup(){
        drivetrain = mock(Drivetrain.class);
        clock = mock(Clock.class);
        when(clock.getTime()).thenReturn(0.0);
        modularDrivetrain = new ModularDrivetrain(drivetrain, clock);
        module = new PowerEfficiencyModule(0.2);
        modularDrivetrain.addModule(module);
    }

    @Test
    public void limitsAccelerationOnUnfixedDT(){
        when(clock.getTime()).thenReturn(0.01);
        modularDrivetrain.tank(0.1, 0.2);
        DrivetrainTestUtils.verifyTank(drivetrain, 0.05, 0.05);

        when(clock.getTime()).thenReturn(0.2);
        modularDrivetrain.tank(0.05, 0);
        DrivetrainTestUtils.verifyTank(drivetrain, 0.05, 0);

        when(clock.getTime()).thenReturn(0.21);
        modularDrivetrain.tank(-0.05, -0.2);
        DrivetrainTestUtils.verifyTank(drivetrain, 0.0, -0.05);
    }

    @Test
    public void limitsAccelerationOnFixedDT(){
        when(clock.getTime()).thenReturn(0.02);
        modularDrivetrain.tank(1, 1);
        DrivetrainTestUtils.verifyTank(drivetrain, 0.1, 0.1);

        when(clock.getTime()).thenReturn(0.04);
        modularDrivetrain.tank(-1, -1);
        DrivetrainTestUtils.verifyTank(drivetrain, 0, 0);

        when(clock.getTime()).thenReturn(0.06);
        modularDrivetrain.tank(-0.2, 0.3);
        DrivetrainTestUtils.verifyTank(drivetrain, -0.1, 0.1);
    }

    @Test
    public void doesNotApplyRateLimitWhileTurningWithTresholdSet(){
        module.setTurnThreshold(0.2);
        
        when(clock.getTime()).thenReturn(0.02);
        modularDrivetrain.tank(1, 1);
        DrivetrainTestUtils.verifyTank(drivetrain, 0.1, 0.1);

        modularDrivetrain.tank(1, 0);
        DrivetrainTestUtils.verifyTank(drivetrain, 1, 0);
    }

    @Test(expected = IllegalArgumentException.class)
    public void throwsWhenRampingTimeIsNegative(){
        new PowerEfficiencyModule(-1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void throwsWhenTurningThresholdIsNegative(){
        new PowerEfficiencyModule(1, -1);
    }

}