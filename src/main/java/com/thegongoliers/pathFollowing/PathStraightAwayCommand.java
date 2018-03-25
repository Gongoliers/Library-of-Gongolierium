package com.thegongoliers.pathFollowing;


public class PathStraightAwayCommand extends PathTaskCommand {

    private double distance;

    public PathStraightAwayCommand(SmartDriveTrainSubsystem drivetrain, double distance) {
        super(drivetrain);
        this.distance = distance;
    }

    public PathStraightAwayCommand(SmartDriveTrainSubsystem drivetrain, double distance, double timeout) {
        super(drivetrain, timeout);
        this.distance = distance;
    }

    @Override
    protected void initialize() {
        drivetrain.resetDistance();
    }

    @Override
    protected void execute() {
        double currentDistance = drivetrain.getCenterDistance();

        drivetrain.tank(drivetrain.getLeftDistanceController().calculate(currentDistance, distance), drivetrain.getRightDistanceController().calculate(currentDistance, distance));
    }

    @Override
    protected boolean isFinished() {
        return drivetrain.getLeftDistanceController().isOnTarget(drivetrain.getCenterDistance(), distance);
    }

    @Override
    protected void end() {
        drivetrain.forward(0);
    }

    @Override
    protected void interrupted() {
        end();
    }
}
