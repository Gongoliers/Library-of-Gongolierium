package com.thegongoliers.output.drivemodules;

import org.junit.Before;
import org.junit.Test;
import org.mockito.AdditionalMatchers;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.buttons.Trigger;

import static org.mockito.Mockito.*;

import java.util.Arrays;

import com.thegongoliers.hardware.Hardware;
import com.thegongoliers.output.drivemodules.DriveModule;
import com.thegongoliers.output.interfaces.Drivetrain;
import com.thegongoliers.output.subsystems.ModularDrivetrain;

/**
 * FortifyModuleTest
 */
public class PrecisionModuleTest {

    private Drivetrain drivetrain;
    private ModularDrivetrain modularDrivetrain;
    private DriveModule module;



    @Before
    public void setup(){
        drivetrain = mock(Drivetrain.class);
        modularDrivetrain = new ModularDrivetrain(drivetrain);
        module = new PrecisionModule(0.5, 0.75);
        modularDrivetrain.addModule(module);
    }

    @Test
    public void allowsPrecisionMovements(){
        modularDrivetrain.arcade(1, 1);
        verifyArcade(1, 1);

        modularDrivetrain.arcade(-1, -1);
        verifyArcade(-1, -1);

        modularDrivetrain.arcade(0.5, 0.2);
        verifyArcade(0.25, 0.008);

        modularDrivetrain.arcade(-0.5, -0.2);
        verifyArcade(-0.25, -0.008);

        module.setValue(PrecisionModule.VALUE_TURN_STRENGTH, 0.5);
        modularDrivetrain.arcade(-0.5, 0.2);
        verifyArcade(-0.25, 0.04);

        modularDrivetrain.arcade(0.5, -0.2);
        verifyArcade(0.25, -0.04);
    }    

    private void verifyArcade(double speed, double turn){
        verify(drivetrain).arcade(AdditionalMatchers.eq(speed, 0.001), AdditionalMatchers.eq(turn, 0.001));
    }

}