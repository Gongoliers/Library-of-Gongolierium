package com.thegongoliers.output.drivemodules;

import org.junit.Before;
import org.junit.Test;
import org.mockito.AdditionalMatchers;


import static org.mockito.Mockito.*;

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

    @Before
    public void setup(){
        drivetrain = mock(Drivetrain.class);
        modularDrivetrain = new ModularDrivetrain(drivetrain);
        module = new PowerEfficiencyModule(0.9, 0.8);
        modularDrivetrain.addModule(module);
    }

    @Test
    public void allowsAccelerationsWithinRange(){
        modularDrivetrain.arcade(0.1, 0.2);
        verifyArcade(0.1, 0.2);

        modularDrivetrain.arcade(0.05, 0);
        verifyArcade(0.05, 0);

        modularDrivetrain.arcade(-0.05, -0.2);
        verifyArcade(-0.05, -0.2);
    }

    @Test
    public void limitsAcceleration(){
        modularDrivetrain.arcade(1, 1);
        verifyArcade(0.1, 0.2);

        modularDrivetrain.arcade(-1, -1);
        verifyArcade(0, 0);

        modularDrivetrain.arcade(-0.2, 0.3);
        verifyArcade(-0.1, 0.2);
    }

    private void verifyArcade(double speed, double turn){
        verify(drivetrain).arcade(AdditionalMatchers.eq(speed, 0.001), AdditionalMatchers.eq(turn, 0.001));
    }

}