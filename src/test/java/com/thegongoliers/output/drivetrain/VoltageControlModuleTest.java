package com.thegongoliers.output.drivetrain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InOrder;
import org.mockito.Mockito;

import static org.mockito.Mockito.*;

import com.thegongoliers.input.time.Clock;
import com.thegongoliers.input.voltage.VoltageSensor;
import com.thegongoliers.output.interfaces.Drivetrain;

public class VoltageControlModuleTest {

    private Drivetrain drivetrain;
    private ModularDrivetrain modularDrivetrain;
    private VoltageControlModule module;
    private VoltageSensor voltageSensor;
    private InOrder inorder;

    @BeforeEach
    public void setup(){
        drivetrain = mock(Drivetrain.class);
        modularDrivetrain = new ModularDrivetrain(drivetrain, mock(Clock.class));
        voltageSensor = mock(VoltageSensor.class);
        module = new VoltageControlModule(8, voltageSensor);
        modularDrivetrain.addModule(module);
        inorder = Mockito.inOrder(drivetrain);
    }

    @Test
    public void keepsVoltageSteady(){
        when(voltageSensor.getVoltage()).thenReturn(12.0);
        modularDrivetrain.tank(1, 0);
        DrivetrainTestUtils.inorderVerifyTank(inorder, drivetrain, 8 / 12.0, 0);

        when(voltageSensor.getVoltage()).thenReturn(8.0);
        modularDrivetrain.tank(1, -0.5);
        DrivetrainTestUtils.inorderVerifyTank(inorder, drivetrain, 1, -0.5);

        when(voltageSensor.getVoltage()).thenReturn(16.0);
        modularDrivetrain.tank(-0.25, 0.5);
        DrivetrainTestUtils.inorderVerifyTank(inorder, drivetrain, -0.125, 0.25);

        when(voltageSensor.getVoltage()).thenReturn(4.0);
        modularDrivetrain.tank(0.25, 1);
        DrivetrainTestUtils.inorderVerifyTank(inorder, drivetrain, 0.5, 1);
    }
   

}