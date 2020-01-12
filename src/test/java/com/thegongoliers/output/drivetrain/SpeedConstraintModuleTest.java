package com.thegongoliers.output.drivetrain;

import org.junit.Before;
import org.junit.Test;
import org.mockito.AdditionalMatchers;


import static org.mockito.Mockito.*;

import com.thegongoliers.input.time.Clock;
import com.thegongoliers.output.interfaces.Drivetrain;

/**
 * SpeedConstraintModuleTest
 */
public class SpeedConstraintModuleTest {

    private Drivetrain drivetrain;
    private ModularDrivetrain modularDrivetrain;
    private SpeedConstraintModule module;

    @Before
    public void setup(){
        drivetrain = mock(Drivetrain.class);
        modularDrivetrain = new ModularDrivetrain(drivetrain, mock(Clock.class));
        module = new SpeedConstraintModule(0.8, false);
        modularDrivetrain.addModule(module);
    }

    @Test
    public void allowsSpeedsWithinConstraint(){
        modularDrivetrain.tank(0.8, 0.5);
        verifyTank(0.8, 0.5);

        modularDrivetrain.tank(0, 0);
        verifyTank(0, 0);

        modularDrivetrain.tank(-0.8, -0.5);
        verifyTank(-0.8, -0.5);
    }

    @Test
    public void clampsSpeedsOutsideRange(){
        modularDrivetrain.tank(1, 1);
        verifyTank(0.8, 0.8);

        modularDrivetrain.tank(-1, -1);
        verifyTank(-0.8, -0.8);
    }

    @Test
    public void canScaleValuesToRange(){
        module.setShouldScaleSpeeds(true);

        modularDrivetrain.tank(1, 1);
        verifyTank(0.8, 0.8);

        modularDrivetrain.tank(0.5, 0.2);
        verifyTank(0.4, 0.16);

        modularDrivetrain.tank(-0.5, -0.2);
        verifyTank(-0.4, -0.16);

        modularDrivetrain.tank(-1, -1);
        verifyTank(-0.8, -0.8);
    }

    private void verifyTank(double left, double right){
        verify(drivetrain).tank(AdditionalMatchers.eq(left, 0.001), AdditionalMatchers.eq(right, 0.001));
    }

}