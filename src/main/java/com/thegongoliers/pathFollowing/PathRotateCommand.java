package com.thegongoliers.pathFollowing;

public class PathRotateCommand extends PathTaskCommand {

    private double angleDegrees;

    public PathRotateCommand(SmartDriveTrainSubsystem drivetrain, double angleDegrees) {
        super(drivetrain);
        this.angleDegrees = angleDegrees;
    }

    @Override
    protected void initialize() {
        drivetrain.resetGyro();
    }

    @Override
    protected void execute() {
        double currentAngle = drivetrain.getAngle();

        drivetrain.rotateRight(drivetrain.getRotateAnglePID().getOutput(currentAngle, angleDegrees));
    }

    @Override
    protected boolean isFinished() {
        return drivetrain.getRotateAnglePID().isAtTargetPosition(drivetrain.getAngle(), angleDegrees);
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
