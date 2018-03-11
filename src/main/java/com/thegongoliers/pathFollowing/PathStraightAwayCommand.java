package com.thegongoliers.pathFollowing;

import com.thegongoliers.input.odometry.Odometry;

public class PathStraightAwayCommand extends PathTaskCommand {

    private double distance;

    public PathStraightAwayCommand(SmartDriveTrainSubsystem drivetrain, double distance) {
        super(drivetrain);
        this.distance = distance;
    }

    @Override
    protected void initialize() {
        drivetrain.resetEncoders();
    }

    @Override
    protected void execute() {
        double currentDistance = getCurrentDistance();

        drivetrain.forward(drivetrain.getDriveDistancePID().getOutput(currentDistance, distance));
    }

    private double getCurrentDistance() {
        double leftDistance = drivetrain.getLeftDistance();
        double rightDistance = drivetrain.getRightDistance();

        return Odometry.getDistance(leftDistance, rightDistance);
    }

    @Override
    protected boolean isFinished() {
        return drivetrain.getDriveDistancePID().isAtTargetPosition(getCurrentDistance(), distance);
    }
}
