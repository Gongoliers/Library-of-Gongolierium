package com.thegongoliers.output;

import com.thegongoliers.mockHardware.output.MockDriveTrain;
import org.junit.Test;

import static org.junit.Assert.*;

public class DriveUtilTest {

    @Test
    public void driveStraight() {

        MockDriveTrain mockDriveTrain = new MockDriveTrain();

        // Forward no correction
        DriveUtil.driveStraight(mockDriveTrain, 1.0, 0, 0.03);
        assertSpeeds(mockDriveTrain, 1.0, 0.0, 0.0001);

        // Reverse no correction
        DriveUtil.driveStraight(mockDriveTrain, -0.5, 0, 0.03);
        assertSpeeds(mockDriveTrain, -0.5, 0.0, 0.0001);

        // Forward, off to the right
        DriveUtil.driveStraight(mockDriveTrain, 1.0, 10, 0.03);
        assertSpeeds(mockDriveTrain, 1.0, -0.3, 0.0001);

        // Forward, off to the left
        DriveUtil.driveStraight(mockDriveTrain, 1.0, -10, 0.05);
        assertSpeeds(mockDriveTrain, 1.0, 0.5, 0.0001);

        // Reverse, off to the right
        DriveUtil.driveStraight(mockDriveTrain, -1.0, 10, 0.03);
        assertSpeeds(mockDriveTrain, -1.0, -0.3, 0.0001);

        // Reverse, off to the left
        DriveUtil.driveStraight(mockDriveTrain, -1.0, -5, 0.03);
        assertSpeeds(mockDriveTrain, -1.0, 0.15, 0.0001);

    }

    private void assertSpeeds(MockDriveTrain mockDriveTrain, double forwardSpeed, double rotationSpeed, double delta){
        assertEquals(forwardSpeed, mockDriveTrain.getForwardSpeed(), delta);
        assertEquals(rotationSpeed, mockDriveTrain.getRotationSpeed(), delta);
    }
}