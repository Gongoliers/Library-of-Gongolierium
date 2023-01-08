package com.thegongoliers.output.drivetrain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.*;

import com.thegongoliers.input.time.Clock;
import com.thegongoliers.output.interfaces.Drivetrain;

/**
 * FortifyModuleTest
 */
public class PrecisionModuleTest {

    private Drivetrain drivetrain;
    private ModularDrivetrain modularDrivetrain;
    private DriveModule module;



    @BeforeEach
    public void setup(){
        drivetrain = mock(Drivetrain.class);
        modularDrivetrain = new ModularDrivetrain(drivetrain, mock(Clock.class));
        module = new PrecisionModule(0.5);
        modularDrivetrain.addModule(module);
    }

    @Test
    public void allowsPrecisionMovements(){
        modularDrivetrain.tank(1, 1);
        DrivetrainTestUtils.verifyTank(drivetrain, 1, 1);

        modularDrivetrain.tank(-1, -1);
        DrivetrainTestUtils.verifyTank(drivetrain, -1, -1);

        modularDrivetrain.tank(0.5, 0.2);
        DrivetrainTestUtils.verifyTank(drivetrain, 0.25, 0.04);

        modularDrivetrain.tank(-0.5, -0.2);
        DrivetrainTestUtils.verifyTank(drivetrain, -0.25, -0.04);
    }    

}