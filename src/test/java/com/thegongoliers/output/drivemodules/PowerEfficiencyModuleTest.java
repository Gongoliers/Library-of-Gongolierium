package com.thegongoliers.output.drivemodules;

import org.junit.Before;
import org.junit.Test;
import org.mockito.AdditionalMatchers;


import static org.mockito.Mockito.*;

import com.thegongoliers.mockHardware.input.MockClock;
import com.thegongoliers.output.drivemodules.DriveModule;
import com.thegongoliers.output.interfaces.Drivetrain;
import com.thegongoliers.output.subsystems.ModularDrivetrain;

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
        module = new PowerEfficiencyModule(0.2, 0.1);
        modularDrivetrain.addModule(module);
    }

    @Test
    public void limitsAccelerationOnUnfixedDT(){
        clock.setTime(0.01);
        modularDrivetrain.arcade(0.1, 0.2);
        verifyArcade(0.05, 0.1);

        clock.setTime(0.2);
        modularDrivetrain.arcade(0.05, 0);
        verifyArcade(0.05, 0);

        clock.setTime(0.21);
        modularDrivetrain.arcade(-0.05, -0.2);
        verifyArcade(0.0, -0.1);
    }

    @Test
    public void limitsAccelerationOnFixedDT(){
        clock.setTime(0.02);
        modularDrivetrain.arcade(1, 1);
        verifyArcade(0.1, 0.2);

        clock.setTime(0.04);
        modularDrivetrain.arcade(-1, -1);
        verifyArcade(0, 0);

        clock.setTime(0.06);
        modularDrivetrain.arcade(-0.2, 0.3);
        verifyArcade(-0.1, 0.2);
    }

    private void verifyArcade(double speed, double turn){
        verify(drivetrain).arcade(AdditionalMatchers.eq(speed, 0.001), AdditionalMatchers.eq(turn, 0.001));
    }

}