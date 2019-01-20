package com.thegongoliers.output;

import com.thegongoliers.mockHardware.output.MockDriveTrain;
import org.junit.Test;

import static org.junit.Assert.*;

public class DriveUtilTest {

    @Test
    public void testTimeToDriveDistanceDeadReckoning(){
        assertEquals(1.0, DriveUtil.timeToDriveDistanceDeadReckoning(10, 10, 1), 0.0001);
        assertEquals(2.0, DriveUtil.timeToDriveDistanceDeadReckoning(10, 10, 0.5), 0.0001);
        assertEquals(22, DriveUtil.timeToDriveDistanceDeadReckoning(55, 5, 0.5), 0.0001);
        assertEquals(Double.POSITIVE_INFINITY, DriveUtil.timeToDriveDistanceDeadReckoning(55, 5, 0.0), 0.0001);
    }

    @Test
    public void testBangBangDriveDistance(){
        MockDriveTrain mockDriveTrain = new MockDriveTrain();

        // Below threshold
        assertFalse(DriveUtil.bangBangDriveDistance(10, 5, 0.1, 1.0, mockDriveTrain));
        assertSpeeds(mockDriveTrain, 1.0, 0, 0.001);

        // In threshold
        assertTrue(DriveUtil.bangBangDriveDistance(10, 9.9, 0.1, 1.0, mockDriveTrain));
        assertSpeeds(mockDriveTrain, 0.0, 0, 0.001);

        assertTrue(DriveUtil.bangBangDriveDistance(10, 10.1, 0.1, 1.0, mockDriveTrain));
        assertSpeeds(mockDriveTrain, 0.0, 0, 0.001);

        assertTrue(DriveUtil.bangBangDriveDistance(10, 10, 0.1, 1.0, mockDriveTrain));
        assertSpeeds(mockDriveTrain, 0.0, 0, 0.001);

        // Above threshold
        assertFalse(DriveUtil.bangBangDriveDistance(10, 15, 0.1, 0.5, mockDriveTrain));
        assertSpeeds(mockDriveTrain, -0.5, 0, 0.001);

        // Negative max speed
        assertFalse(DriveUtil.bangBangDriveDistance(10, 5, 0.1, -1.0, mockDriveTrain));
        assertSpeeds(mockDriveTrain, 0.0, 0, 0.001);
    }


    @Test
    public void testFeedForwardControl(){
        MockDriveTrain mockDriveTrain = new MockDriveTrain();

        // Both (speeding up)
        DriveUtil.feedForwardControl(10, 2, 0.01, 0.1, mockDriveTrain);
        assertSpeeds(mockDriveTrain, 0.3, 0.0, 0.0001);

        // Only velocity (constant speed)
        DriveUtil.feedForwardControl(10, 0, 0.02, 0.1, mockDriveTrain);
        assertSpeeds(mockDriveTrain, 0.2, 0.0, 0.0001);


        // Only acceleration (speeding up)
        DriveUtil.feedForwardControl(0, 3, 0.1, 0.01, mockDriveTrain);
        assertSpeeds(mockDriveTrain, 0.03, 0.0, 0.0001);

        // None
        DriveUtil.feedForwardControl(0, 0, 0.01, 0.1, mockDriveTrain);
        assertSpeeds(mockDriveTrain, 0, 0.0, 0.0001);

        // Negative velocity (slowing down)
        DriveUtil.feedForwardControl(-10, 1, 0.02, 0.1, mockDriveTrain);
        assertSpeeds(mockDriveTrain, -0.1, 0.0, 0.0001);

        // Negative acceleration (slowing down)
        DriveUtil.feedForwardControl(5, -2, 0.01, 0.01, mockDriveTrain);
        assertSpeeds(mockDriveTrain, 0.03, 0.0, 0.0001);

        // Both negative (speeding up)
        DriveUtil.feedForwardControl(-10, -1, 0.01, 0.1, mockDriveTrain);
        assertSpeeds(mockDriveTrain, -0.2, 0.0, 0.0001);

        // Over 1
        DriveUtil.feedForwardControl(100, 0, 0.01, 0.1, mockDriveTrain);
        assertSpeeds(mockDriveTrain, 1, 0.0, 0.0001);
    }


    @Test
    public void testDriveStraight() {

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