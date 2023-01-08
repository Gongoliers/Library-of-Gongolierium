package com.thegongoliers.output.drivetrain;

import com.thegongoliers.input.odometry.AverageEncoderSensor;
import com.thegongoliers.output.control.PIDController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.mockito.InOrder;
import org.mockito.Mockito;

import edu.wpi.first.wpilibj.interfaces.Gyro;
import static org.mockito.Mockito.*;

import com.thegongoliers.input.odometry.EncoderSensor;
import com.thegongoliers.input.time.Clock;
import com.thegongoliers.output.interfaces.Drivetrain;
import com.thegongoliers.paths.SimplePath;

public class PathFollowerModuleTest {

    private Drivetrain drivetrain;
    private ModularDrivetrain modularDrivetrain;
    private PathFollowerModule module;
    private EncoderSensor encoder1, encoder2;
    private Gyro gyro;
    private InOrder inorder;
    private Clock clock;

    @BeforeEach
    public void setup(){
        drivetrain = mock(Drivetrain.class);
        clock = mock(Clock.class);
        modularDrivetrain = new ModularDrivetrain(drivetrain, clock);
        encoder1 = mock(EncoderSensor.class);
        encoder2 = mock(EncoderSensor.class);
        gyro = mock(Gyro.class);
        var encoders = new AverageEncoderSensor(encoder1, encoder2);
        var forward = new PIDController(0.1, 0, 0);
        forward.setPositionTolerance(0.2);
        var turn = new PIDController(0.2, 0, 0);
        turn.setPositionTolerance(0.2);
        module = new PathFollowerModule(gyro, encoders, forward, turn);
        modularDrivetrain.addModule(module);
        inorder = Mockito.inOrder(drivetrain);
    }

    @Test
    public void canFollowASingleStraightPath(){
        when(encoder1.getDistance()).thenReturn(0.0);
        when(encoder2.getDistance()).thenReturn(0.0);
        when(gyro.getAngle()).thenReturn(0.0);

        var path = new SimplePath();
        path.addStraightAway(10.0);

        assertFalse(module.overridesUser());
        module.startFollowingPath(path);
        assertTrue(module.isFollowingPath());
        assertTrue(module.overridesUser());

        when(clock.getTime()).thenReturn(0.01);
        modularDrivetrain.tank(1, 1);
        DrivetrainTestUtils.inorderVerifyTank(inorder, drivetrain, 1, 1);

        when(encoder1.getDistance()).thenReturn(8.0);
        when(encoder2.getDistance()).thenReturn(8.0);

        when(clock.getTime()).thenReturn(0.02);
        modularDrivetrain.tank(1, 1);
        DrivetrainTestUtils.inorderVerifyTank(inorder, drivetrain, 0.2, 0.2);

        when(encoder1.getDistance()).thenReturn(10.0);
        when(encoder2.getDistance()).thenReturn(9.6);

        when(clock.getTime()).thenReturn(0.03);
        modularDrivetrain.tank(1, 1);
        DrivetrainTestUtils.inorderVerifyTank(inorder, drivetrain, 0.0, 0.0);

        when(clock.getTime()).thenReturn(0.04);
        modularDrivetrain.tank(1, 1);
        DrivetrainTestUtils.inorderVerifyTank(inorder, drivetrain, 0, 0);
        assertFalse(module.isFollowingPath());

        when(clock.getTime()).thenReturn(0.05);
        modularDrivetrain.tank(0.5, 0.5);
        DrivetrainTestUtils.inorderVerifyTank(inorder, drivetrain, 0.5, 0.5);
    }

    @Test
    public void canFollowASingleRotationPath(){
        when(gyro.getAngle()).thenReturn(0.0);

        var path = new SimplePath();
        path.addRotation(10.0);

        module.startFollowingPath(path);
        assertTrue(module.isFollowingPath());

        modularDrivetrain.tank(1, 1);
        DrivetrainTestUtils.inorderVerifyArcade(inorder, drivetrain, 0, 1);

        when(gyro.getAngle()).thenReturn(6.0);

        when(clock.getTime()).thenReturn(0.01);
        modularDrivetrain.tank(1, 1);
        DrivetrainTestUtils.inorderVerifyArcade(inorder, drivetrain, 0, 0.8);

        when(gyro.getAngle()).thenReturn(9.8);

        when(clock.getTime()).thenReturn(0.02);
        modularDrivetrain.tank(1, 1);
        DrivetrainTestUtils.inorderVerifyArcade(inorder, drivetrain, 0, 0.0);

        when(clock.getTime()).thenReturn(0.03);
        modularDrivetrain.tank(1, 1);
        DrivetrainTestUtils.inorderVerifyTank(inorder, drivetrain, 0, 0);
        assertFalse(module.isFollowingPath());

        when(clock.getTime()).thenReturn(0.04);
        modularDrivetrain.tank(0.5, 0.5);
        DrivetrainTestUtils.inorderVerifyTank(inorder, drivetrain, 0.5, 0.5);
    }

    @Test
    public void canFollowAMultiStepPath(){
        when(gyro.getAngle()).thenReturn(0.0);
        when(encoder1.getDistance()).thenReturn(0.0);
        when(encoder2.getDistance()).thenReturn(0.0);

        var path = new SimplePath();
        path.addRotation(10.0);
        path.addStraightAway(5.0);

        module.startFollowingPath(path);
        assertTrue(module.isFollowingPath());

        // Rotation
        modularDrivetrain.tank(1, 1);
        DrivetrainTestUtils.inorderVerifyArcade(inorder, drivetrain, 0, 1);
        when(gyro.getAngle()).thenReturn(6.0);

        when(clock.getTime()).thenReturn(0.01);
        modularDrivetrain.tank(1, 1);
        DrivetrainTestUtils.inorderVerifyArcade(inorder, drivetrain, 0, 0.8);
        when(gyro.getAngle()).thenReturn(9.8);

        when(clock.getTime()).thenReturn(0.02);
        modularDrivetrain.tank(1, 1);
        DrivetrainTestUtils.inorderVerifyArcade(inorder, drivetrain, 0, 0.0);

        assertTrue(module.isFollowingPath());

        // Straight away
        when(clock.getTime()).thenReturn(0.03);
        modularDrivetrain.tank(1, 1);
        DrivetrainTestUtils.inorderVerifyTank(inorder, drivetrain, 0.5, 0.5);
        when(encoder1.getDistance()).thenReturn(4.0);
        when(encoder2.getDistance()).thenReturn(4.0);

        when(clock.getTime()).thenReturn(0.04);
        modularDrivetrain.tank(1, 1);
        DrivetrainTestUtils.inorderVerifyTank(inorder, drivetrain, 0.1, 0.1);
        when(encoder1.getDistance()).thenReturn(5.0);
        when(encoder2.getDistance()).thenReturn(5.0);

        when(clock.getTime()).thenReturn(0.05);
        modularDrivetrain.tank(1, 1);
        DrivetrainTestUtils.inorderVerifyTank(inorder, drivetrain, 0.0, 0.0);

        // Done
        when(clock.getTime()).thenReturn(0.06);
        modularDrivetrain.tank(1, 1);
        DrivetrainTestUtils.inorderVerifyTank(inorder, drivetrain, 0, 0);
        assertFalse(module.isFollowingPath());

        when(clock.getTime()).thenReturn(0.07);
        modularDrivetrain.tank(0.5, 0.5);
        DrivetrainTestUtils.inorderVerifyTank(inorder, drivetrain, 0.5, 0.5);
    }
}