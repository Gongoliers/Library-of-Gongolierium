package com.thegongoliers.pathFollowing;

import edu.wpi.first.wpilibj.command.Command;

public abstract class PathTaskCommand extends Command {

    protected SmartDriveTrainSubsystem drivetrain;

    public PathTaskCommand(SmartDriveTrainSubsystem drivetrain){
        requires(drivetrain);
        this.drivetrain = drivetrain;
    }

    @Override
    abstract protected void execute();

    @Override
    abstract protected boolean isFinished();

    @Override
    protected void end() {
        drivetrain.stop();
    }

    @Override
    protected void interrupted() {
        drivetrain.stop();
    }
}
