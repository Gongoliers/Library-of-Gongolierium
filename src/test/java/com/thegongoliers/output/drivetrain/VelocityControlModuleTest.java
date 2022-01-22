package com.thegongoliers.output.drivetrain;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InOrder;

import static org.mockito.Mockito.*;

import com.thegongoliers.input.odometry.EncoderSensor;
import com.thegongoliers.input.time.Clock;
import com.thegongoliers.output.interfaces.Drivetrain;

public class VelocityControlModuleTest {

    private Drivetrain drivetrain;
    private ModularDrivetrain modularDrivetrain;
    private VelocityControlModule module;
    private EncoderSensor encoder1, encoder2;
    private InOrder inorder;

    @Before
    public void setup(){
        drivetrain = mock(Drivetrain.class);
        modularDrivetrain = new ModularDrivetrain(drivetrain, mock(Clock.class));
        encoder1 = mock(EncoderSensor.class);
        encoder2 = mock(EncoderSensor.class);
        module = new VelocityControlModule(encoder1, encoder2, 1, 0.1);
        modularDrivetrain.addModule(module);
        inorder = inOrder(drivetrain);
    }

    @Test
    public void canSetVelocity(){
        when(encoder1.getVelocity()).thenReturn(0.0);
        when(encoder2.getVelocity()).thenReturn(0.0);
        modularDrivetrain.tank(1, -1);
        DrivetrainTestUtils.inorderVerifyTank(inorder, drivetrain, 0.1, -0.1);

        when(encoder1.getVelocity()).thenReturn(0.5);
        when(encoder2.getVelocity()).thenReturn(-0.5);
        modularDrivetrain.tank(1, -1);
        DrivetrainTestUtils.inorderVerifyTank(inorder, drivetrain, 0.15, -0.15);

        when(encoder1.getVelocity()).thenReturn(1.0);
        when(encoder2.getVelocity()).thenReturn(-1.0);
        modularDrivetrain.tank(1, -1);
        DrivetrainTestUtils.inorderVerifyTank(inorder, drivetrain, 0.15, -0.15);

        when(encoder1.getVelocity()).thenReturn(1.5);
        when(encoder2.getVelocity()).thenReturn(-1.5);
        modularDrivetrain.tank(1, -1);
        DrivetrainTestUtils.inorderVerifyTank(inorder, drivetrain, 0.1, -0.1);

        when(encoder1.getVelocity()).thenReturn(1.0);
        when(encoder2.getVelocity()).thenReturn(-1.0);
        modularDrivetrain.tank(0, 0);
        DrivetrainTestUtils.inorderVerifyTank(inorder, drivetrain, 0, 0);
    }

}