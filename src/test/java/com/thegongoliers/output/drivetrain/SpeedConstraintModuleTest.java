package com.thegongoliers.output.drivetrain;

import org.junit.Before;
import org.junit.Test;
import org.mockito.AdditionalMatchers;


import static org.mockito.Mockito.*;

import com.thegongoliers.output.interfaces.Drivetrain;

/**
 * SpeedConstraintModuleTest
 */
public class SpeedConstraintModuleTest {

    private Drivetrain drivetrain;
    private ModularDrivetrain modularDrivetrain;
    private DriveModule module;

    @Before
    public void setup(){
        drivetrain = mock(Drivetrain.class);
        modularDrivetrain = new ModularDrivetrain(drivetrain);
        module = new SpeedConstraintModule(0.8, 0.5, false);
        modularDrivetrain.addModule(module);
    }

    @Test
    public void allowsSpeedsWithinConstraint(){
        modularDrivetrain.arcade(0.8, 0.5);
        verifyArcade(0.8, 0.5);

        modularDrivetrain.arcade(0, 0);
        verifyArcade(0, 0);

        modularDrivetrain.arcade(-0.8, -0.5);
        verifyArcade(-0.8, -0.5);
    }

    @Test
    public void clampsSpeedsOutsideRange(){
        modularDrivetrain.arcade(1, 1);
        verifyArcade(0.8, 0.5);

        modularDrivetrain.arcade(-1, -1);
        verifyArcade(-0.8, -0.5);
    }

    @Test
    public void canScaleValuesToRange(){
        module.setValue(SpeedConstraintModule.VALUE_SCALE_SPEEDS, true);

        modularDrivetrain.arcade(1, 1);
        verifyArcade(0.8, 0.5);

        modularDrivetrain.arcade(0.5, 0.2);
        verifyArcade(0.4, 0.1);

        modularDrivetrain.arcade(-0.5, -0.2);
        verifyArcade(-0.4, -0.1);

        modularDrivetrain.arcade(-1, -1);
        verifyArcade(-0.8, -0.5);
    }

    private void verifyArcade(double speed, double turn){
        verify(drivetrain).arcade(AdditionalMatchers.eq(speed, 0.001), AdditionalMatchers.eq(turn, 0.001));
    }

}