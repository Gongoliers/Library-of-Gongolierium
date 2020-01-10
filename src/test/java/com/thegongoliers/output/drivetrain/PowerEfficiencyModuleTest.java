package com.thegongoliers.output.drivetrain;

import org.junit.Before;
import org.junit.Test;
import org.mockito.AdditionalMatchers;


import static org.mockito.Mockito.*;

import com.thegongoliers.mockHardware.input.MockClock;
import com.thegongoliers.output.interfaces.Drivetrain;

/**
 * PowerEfficiencyModuleTest
 */
public class PowerEfficiencyModuleTest {

    private Drivetrain drivetrain;
    private ModularDrivetrain modularDrivetrain;
    private DriveModule module;
    private MockClock clock;

    @Before
    public void setup(){
        drivetrain = mock(Drivetrain.class);
        clock = new MockClock();
        clock.setTime(0);
        modularDrivetrain = new ModularDrivetrain(drivetrain, clock);
        module = new PowerEfficiencyModule(0.2);
        modularDrivetrain.addModule(module);
    }

    @Test
    public void limitsAccelerationOnUnfixedDT(){
        clock.setTime(0.01);
        modularDrivetrain.tank(0.1, 0.2);
        DrivetrainTestUtils.verifyTank(drivetrain, 0.05, 0.05);

        clock.setTime(0.2);
        modularDrivetrain.tank(0.05, 0);
        DrivetrainTestUtils.verifyTank(drivetrain, 0.05, 0);

        clock.setTime(0.21);
        modularDrivetrain.tank(-0.05, -0.2);
        DrivetrainTestUtils.verifyTank(drivetrain, 0.0, -0.05);
    }

    @Test
    public void limitsAccelerationOnFixedDT(){
        clock.setTime(0.02);
        modularDrivetrain.tank(1, 1);
        DrivetrainTestUtils.verifyTank(drivetrain, 0.1, 0.1);

        clock.setTime(0.04);
        modularDrivetrain.tank(-1, -1);
        DrivetrainTestUtils.verifyTank(drivetrain, 0, 0);

        clock.setTime(0.06);
        modularDrivetrain.tank(-0.2, 0.3);
        DrivetrainTestUtils.verifyTank(drivetrain, -0.1, 0.1);
    }

    @Test
    public void doesNotApplyRateLimitWhileTurningWithTresholdSet(){
        module.setValue(PowerEfficiencyModule.VALUE_TURN_THRESHOLD, 0.2);
        
        clock.setTime(0.02);
        modularDrivetrain.tank(1, 1);
        DrivetrainTestUtils.verifyTank(drivetrain, 0.1, 0.1);

        modularDrivetrain.tank(1, 0);
        DrivetrainTestUtils.verifyTank(drivetrain, 1, 0);
    }

}