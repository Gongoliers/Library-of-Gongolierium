package com.thegongoliers.output.drivetrain;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InOrder;
import org.mockito.Mockito;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.interfaces.Gyro;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import java.util.List;
import com.thegongoliers.input.time.Clock;
import com.thegongoliers.output.interfaces.Drivetrain;
import com.thegongoliers.paths.SimplePath;

public class PathFollowerModuleTest {

    private Drivetrain drivetrain;
    private ModularDrivetrain modularDrivetrain;
    private PathFollowerModule module;
    private Encoder encoder1, encoder2;
    private Gyro gyro;
    private InOrder inorder;

    @Before
    public void setup(){
        drivetrain = mock(Drivetrain.class);
        modularDrivetrain = new ModularDrivetrain(drivetrain, mock(Clock.class));
        encoder1 = mock(Encoder.class);
        encoder2 = mock(Encoder.class);
        gyro = mock(Gyro.class);
        var encoders = List.of(encoder1, encoder2);
        module = new PathFollowerModule(gyro, encoders, 0.1, 0.2);
        module.setForwardTolerance(0.2);
        module.setTurnTolerance(0.2);
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

        modularDrivetrain.tank(1, 1);
        DrivetrainTestUtils.inorderVerifyTank(inorder, drivetrain, 1, 1);

        when(encoder1.getDistance()).thenReturn(8.0);
        when(encoder2.getDistance()).thenReturn(8.0);

        modularDrivetrain.tank(1, 1);
        DrivetrainTestUtils.inorderVerifyTank(inorder, drivetrain, 0.2, 0.2);

        when(encoder1.getDistance()).thenReturn(10.0);
        when(encoder2.getDistance()).thenReturn(9.6);

        modularDrivetrain.tank(1, 1);
        DrivetrainTestUtils.inorderVerifyTank(inorder, drivetrain, 0.0, 0.0);

        modularDrivetrain.tank(1, 1);
        DrivetrainTestUtils.inorderVerifyTank(inorder, drivetrain, 0, 0);
        assertFalse(module.isFollowingPath());

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

        modularDrivetrain.tank(1, 1);
        DrivetrainTestUtils.inorderVerifyArcade(inorder, drivetrain, 0, 0.8);

        when(gyro.getAngle()).thenReturn(9.8);

        modularDrivetrain.tank(1, 1);
        DrivetrainTestUtils.inorderVerifyArcade(inorder, drivetrain, 0, 0.0);

        modularDrivetrain.tank(1, 1);
        DrivetrainTestUtils.inorderVerifyTank(inorder, drivetrain, 0, 0);
        assertFalse(module.isFollowingPath());

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
        modularDrivetrain.tank(1, 1);
        DrivetrainTestUtils.inorderVerifyArcade(inorder, drivetrain, 0, 0.8);
        when(gyro.getAngle()).thenReturn(9.8);
        modularDrivetrain.tank(1, 1);
        DrivetrainTestUtils.inorderVerifyArcade(inorder, drivetrain, 0, 0.0);

        assertTrue(module.isFollowingPath());

        // Straight away
        verify(gyro, Mockito.times(3)).reset();
        verify(encoder1, Mockito.times(3)).reset();
        verify(encoder2, Mockito.times(3)).reset();
        modularDrivetrain.tank(1, 1);
        DrivetrainTestUtils.inorderVerifyTank(inorder, drivetrain, 0.5, 0.5);
        when(encoder1.getDistance()).thenReturn(4.0);
        when(encoder2.getDistance()).thenReturn(4.0);
        modularDrivetrain.tank(1, 1);
        DrivetrainTestUtils.inorderVerifyTank(inorder, drivetrain, 0.1, 0.1);
        when(encoder1.getDistance()).thenReturn(5.0);
        when(encoder2.getDistance()).thenReturn(5.0);
        modularDrivetrain.tank(1, 1);
        DrivetrainTestUtils.inorderVerifyTank(inorder, drivetrain, 0.0, 0.0);

        // Done
        modularDrivetrain.tank(1, 1);
        DrivetrainTestUtils.inorderVerifyTank(inorder, drivetrain, 0, 0);
        assertFalse(module.isFollowingPath());

        modularDrivetrain.tank(0.5, 0.5);
        DrivetrainTestUtils.inorderVerifyTank(inorder, drivetrain, 0.5, 0.5);
    }
}