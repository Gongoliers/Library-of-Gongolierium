package com.thegongoliers.output.drivetrain;

import static org.mockito.Mockito.verify;

import com.thegongoliers.output.interfaces.Drivetrain;

import org.mockito.AdditionalMatchers;
import org.mockito.InOrder;

class DrivetrainTestUtils {
    public static void verifyTank(Drivetrain drivetrain, double left, double right){
        verify(drivetrain).tank(AdditionalMatchers.eq(left, 0.001), AdditionalMatchers.eq(right, 0.001));
    }

    public static void verifyArcade(Drivetrain drivetrain, double speed, double turn){
        DriveSpeed tank = DriveSpeed.fromArcade(speed, turn);
        double left = tank.getLeftSpeed();
        double right = tank.getRightSpeed();
        verify(drivetrain).tank(AdditionalMatchers.eq(left, 0.001), AdditionalMatchers.eq(right, 0.001));
    }

    public static void inorderVerifyTank(InOrder inorder, Drivetrain drivetrain, double left, double right){
        inorder.verify(drivetrain).tank(AdditionalMatchers.eq(left, 0.001), AdditionalMatchers.eq(right, 0.001));
    }
}