package com.thegongoliers.output.interfaces;

import com.thegongoliers.pathFollowing.MotionProfileController;

public interface SmartDrivetrain extends DriveTrainInterface {

    double getLeftDistance();

    double getRightDistance();

    void resetDistance();

    double getHeading();

    void resetHeading();

    MotionProfileController getLeftDistanceController();
    
    MotionProfileController getRightDistanceController();

    MotionProfileController getHeadingController();

    double getMaxVelocity();

    double getMaxAcceleration();

    double getMaxJerk();

    double getWheelbaseWidth();
}
