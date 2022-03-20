package com.thegongoliers.output.drivetrain;

import com.thegongoliers.output.control.PIDController;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InOrder;
import org.mockito.Mockito;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;

import com.thegongoliers.input.time.Clock;
import com.thegongoliers.input.vision.TargetingCamera;
import com.thegongoliers.output.interfaces.Drivetrain;

public class TargetAlignmentModuleTest {

    private Drivetrain drivetrain;
    private ModularDrivetrain modularDrivetrain;
    private TargetAlignmentModule module;
    private TargetingCamera camera;
    private InOrder inorder;
    private Clock clock;

    @Before
    public void setup(){
        drivetrain = mock(Drivetrain.class);
        clock = mock(Clock.class);
        modularDrivetrain = new ModularDrivetrain(drivetrain, clock);
        camera = mock(TargetingCamera.class);
        var aim = new PIDController(0.1, 0, 0);
        var range = new PIDController(0.2, 0, 0);
        aim.setPositionTolerance(0.1);
        range.setPositionTolerance(0.1);
        module = new TargetAlignmentModule(camera, aim, range, false);
        modularDrivetrain.addModule(module);
        inorder = Mockito.inOrder(drivetrain);
    }

    @Test
    public void canSeek(){
        module.setShouldSeek(true);
        module.setSeekSpeed(0.2);
        when(camera.hasTarget()).thenReturn(false);
        assertFalse(module.overridesUser());
        module.align(0, 50);
        assertTrue(module.isAligning());
        assertTrue(module.overridesUser());
        modularDrivetrain.tank(1, 1);
        DrivetrainTestUtils.inorderVerifyArcade(inorder, drivetrain, 0, 0.2);
        modularDrivetrain.tank(0.75, 1);
        DrivetrainTestUtils.inorderVerifyArcade(inorder, drivetrain, 0, 0.2);
    }

    @Test
    public void seeksUntilTargetIsFound(){
        module.setShouldSeek(true);
        module.setSeekSpeed(0.2);
        when(camera.hasTarget()).thenReturn(false);
        module.align(0, 50);
        assertTrue(module.isAligning());
        modularDrivetrain.tank(1, 1);
        DrivetrainTestUtils.inorderVerifyArcade(inorder, drivetrain, 0, 0.2);
        when(camera.hasTarget()).thenReturn(true);
        when(camera.getHorizontalOffset()).thenReturn(1.0);
        when(camera.getTargetArea()).thenReturn(25.0);
        modularDrivetrain.tank(0.75, 1);
        DrivetrainTestUtils.inorderVerifyArcade(inorder, drivetrain, 1, -0.1);
    }

    @Test
    public void doesNothingIfCantSeekOrFindTarget(){
        module.setShouldSeek(false);
        when(camera.hasTarget()).thenReturn(false);
        module.align(0, 50);
        assertTrue(module.isAligning());
        modularDrivetrain.tank(1, 1);
        DrivetrainTestUtils.inorderVerifyTank(inorder, drivetrain, 1, 1);
        modularDrivetrain.tank(0.75, 1);
        DrivetrainTestUtils.inorderVerifyTank(inorder, drivetrain, 0.75, 1);
    }

    @Test
    public void alignsWithOnlyAim(){
        when(camera.hasTarget()).thenReturn(true);
        when(camera.getHorizontalOffset()).thenReturn(10.0);
        when(camera.getTargetArea()).thenReturn(25.0);
        var aim = new PIDController(0.1, 0, 0);
        aim.setPositionTolerance(0.1);
        module.setRangeController(null);
        module.setAimController(aim);
        module.align(0, 0);
        assertTrue(module.isAligning());

        modularDrivetrain.tank(1, 1);
        DrivetrainTestUtils.inorderVerifyArcade(inorder, drivetrain, 0, -1);
        when(camera.getHorizontalOffset()).thenReturn(5.0);

        when(clock.getTime()).thenReturn(0.01);
        modularDrivetrain.tank(1, 1);
        DrivetrainTestUtils.inorderVerifyArcade(inorder, drivetrain, 0, -0.5);
        when(camera.getHorizontalOffset()).thenReturn(0.1);

        when(clock.getTime()).thenReturn(0.02);
        modularDrivetrain.tank(1, 1);
        DrivetrainTestUtils.inorderVerifyTank(inorder, drivetrain, 1, 1);
        assertFalse(module.isAligning());
    }

    @Test
    public void alignsWithOnlyRange(){
        when(camera.hasTarget()).thenReturn(true);
        when(camera.getHorizontalOffset()).thenReturn(10.0);
        when(camera.getTargetArea()).thenReturn(25.0);
        var range = new PIDController(0.1, 0, 0);
        range.setPositionTolerance(0.1);
        module.setAimController(null);
        module.setRangeController(range);
        module.align(0, 50);
        assertTrue(module.isAligning());
        modularDrivetrain.tank(1, 1);
        DrivetrainTestUtils.inorderVerifyArcade(inorder, drivetrain, 1, 0);
        when(camera.getTargetArea()).thenReturn(45.0);

        when(clock.getTime()).thenReturn(0.01);
        modularDrivetrain.tank(1, 1);
        DrivetrainTestUtils.inorderVerifyArcade(inorder, drivetrain, 0.5, 0);
        when(camera.getTargetArea()).thenReturn(50.0);

        when(clock.getTime()).thenReturn(0.02);
        modularDrivetrain.tank(1, 1);
        DrivetrainTestUtils.inorderVerifyTank(inorder, drivetrain, 1, 1);
        assertFalse(module.isAligning());
    }

    @Test
    public void alignsToTarget(){
        when(camera.hasTarget()).thenReturn(true);
        when(camera.getHorizontalOffset()).thenReturn(10.0);
        when(camera.getTargetArea()).thenReturn(25.0);
        var aim = new PIDController(0.2, 0, 0);
        aim.setPositionTolerance(0.2);
        var range = new PIDController(0.1, 0, 0);
        range.setPositionTolerance(0.1);
        module.setAimController(aim);
        module.setRangeController(range);
        module.align(0, 50);
        assertTrue(module.isAligning());
        modularDrivetrain.tank(1, 1);
        DrivetrainTestUtils.inorderVerifyArcade(inorder, drivetrain, 1, -1);
        when(camera.getTargetArea()).thenReturn(45.0);
        when(camera.getHorizontalOffset()).thenReturn(2.5);

        when(clock.getTime()).thenReturn(0.01);
        modularDrivetrain.tank(1, 1);
        DrivetrainTestUtils.inorderVerifyArcade(inorder, drivetrain, 0.5, -0.5);
        when(camera.getTargetArea()).thenReturn(50.0);
        when(camera.getHorizontalOffset()).thenReturn(1.0);

        when(clock.getTime()).thenReturn(0.02);
        modularDrivetrain.tank(1, 1);
        DrivetrainTestUtils.inorderVerifyArcade(inorder, drivetrain, 0, -0.2);
        when(camera.getHorizontalOffset()).thenReturn(0.1);

        when(clock.getTime()).thenReturn(0.03);
        modularDrivetrain.tank(1, 1);
        DrivetrainTestUtils.inorderVerifyTank(inorder, drivetrain, 1, 1);
        assertFalse(module.isAligning());
    }

    @Test
    public void canStopAlignment(){
        when(camera.hasTarget()).thenReturn(true);
        when(camera.getHorizontalOffset()).thenReturn(10.0);
        when(camera.getTargetArea()).thenReturn(25.0);
        module.align(0, 50);
        assertTrue(module.isAligning());
        module.stopAligning();   
        assertFalse(module.isAligning());     
        modularDrivetrain.tank(1, 1);
        DrivetrainTestUtils.inorderVerifyTank(inorder, drivetrain, 1, 1);
    }

}