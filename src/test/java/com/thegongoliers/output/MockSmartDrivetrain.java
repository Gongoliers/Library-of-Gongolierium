package com.thegongoliers.output;

import com.thegongoliers.pathFollowing.controllers.MotionProfileController;
import com.thegongoliers.pathFollowing.SmartDriveTrainSubsystem;

public class MockSmartDrivetrain extends SmartDriveTrainSubsystem {
    @Override
    public double getLeftDistance() {
        return 0;
    }

    @Override
    public double getRightDistance() {
        return 0;
    }

    @Override
    public void resetDistance() {

    }

    @Override
    public double getHeading() {
        return 0;
    }

    @Override
    public void resetHeading() {

    }

    @Override
    public MotionProfileController getLeftDistanceController() {
        return null;
    }

    @Override
    public MotionProfileController getRightDistanceController() {
        return null;
    }

    @Override
    public MotionProfileController getHeadingController() {
        return null;
    }

    @Override
    public double getMaxVelocity() {
        return 0;
    }

    @Override
    public double getMaxAcceleration() {
        return 0;
    }

    @Override
    public double getMaxJerk() {
        return 0;
    }

    @Override
    public double getWheelbaseWidth() {
        return 0;
    }

    @Override
    public void forward(double speed) {

    }

    @Override
    public void backward(double speed) {

    }

    @Override
    public void rotateLeft(double speed) {

    }

    @Override
    public void rotateRight(double speed) {

    }

    @Override
    public void arcade(double speed, double rotation) {

    }

    @Override
    public void tank(double left, double right) {

    }

    @Override
    public void stop() {

    }

    @Override
    protected void initDefaultCommand() {

    }
}
