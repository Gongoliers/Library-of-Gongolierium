package com.thegongoliers.output.drivetrain;

import org.junit.Before;
import org.junit.Test;
import org.mockito.AdditionalMatchers;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.buttons.Trigger;

import static org.mockito.Mockito.*;

import java.util.Arrays;

import com.thegongoliers.hardware.Hardware;
import com.thegongoliers.mockHardware.input.MockSwitch;
import com.thegongoliers.output.interfaces.Drivetrain;

/**
 * TractionControlModuleTest
 */
public class TractionControlModuleTest {

    private Drivetrain drivetrain;
    private ModularDrivetrain modularDrivetrain;
    private DriveModule module;
    private Encoder encoder1, encoder2;

    @Before
    public void setup(){
        drivetrain = mock(Drivetrain.class);
        modularDrivetrain = new ModularDrivetrain(drivetrain);
        encoder1 = mock(Encoder.class);
        encoder2 = mock(Encoder.class);
        module = new TractionControlModule(encoder1, encoder2, 0.1);
        modularDrivetrain.addModule(module);
    }

    @Test
    public void doesNotApplyWhileTurning(){
        when(encoder1.getRate()).thenReturn(1.0);
        when(encoder2.getRate()).thenReturn(0.5);
        modularDrivetrain.arcade(1, 0.5);
        DrivetrainTestUtils.verifyArcade(drivetrain, 1, 0.5);

        when(encoder1.getRate()).thenReturn(1.0);
        when(encoder2.getRate()).thenReturn(-0.5);
        modularDrivetrain.tank(1, -0.5);
        DrivetrainTestUtils.verifyTank(drivetrain, 1, -0.5);
    }

    @Test
    public void appliesWhileNotTurning(){
        // Left side slipping
        when(encoder1.getRate()).thenReturn(2.0);
        when(encoder2.getRate()).thenReturn(1.0);
        modularDrivetrain.tank(1, 1);
        DrivetrainTestUtils.verifyTank(drivetrain, 0.9, 1);

        // Right side slipping
        when(encoder1.getRate()).thenReturn(1.0);
        when(encoder2.getRate()).thenReturn(3.0);
        modularDrivetrain.tank(1, 1);
        DrivetrainTestUtils.verifyTank(drivetrain, 1, 0.8);
    }

}