package com.thegongoliers.pathFollowing;

public class PathRotateCommand extends PathTaskCommand {

    private double angleDegrees;

    public PathRotateCommand(SmartDriveTrainSubsystem drivetrain, double angleDegrees) {
        super(drivetrain);
        this.angleDegrees = angleDegrees;
    }

    public PathRotateCommand(SmartDriveTrainSubsystem drivetrain, double angleDegrees, double timeout) {
        super(drivetrain, timeout);
        this.angleDegrees = angleDegrees;
    }

    @Override
    protected void initialize() {
        drivetrain.resetHeading();
    }

    @Override
    protected void execute() {
        double currentAngle = drivetrain.getHeading();

        drivetrain.rotateRight(drivetrain.getHeadingController().calculate(currentAngle, angleDegrees));
    }

    @Override
    protected boolean isFinished() {
        return drivetrain.getHeadingController().isOnTarget(drivetrain.getHeading(), angleDegrees);
    }

    @Override
    protected void end() {
        drivetrain.rotateRight(0);
    }

    @Override
    protected void interrupted() {
        end();
    }
}
