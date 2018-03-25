package com.thegongoliers.pathFollowing;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;
import jaci.pathfinder.Waypoint;
import jaci.pathfinder.modifiers.TankModifier;

import java.util.List;

public class FollowSmoothPathCommand extends Command {

    private SmartDriveTrainSubsystem drivetrain;
    private TankModifier path;
    private double timestep = 20 / 1000.0;
    private double startTime = 0;

    public FollowSmoothPathCommand(SmartDriveTrainSubsystem drivetrain, List<Waypoint> waypoints){
        requires(drivetrain);
        this.drivetrain = drivetrain;
        path = PathFollower.generatePath(drivetrain, waypoints, timestep);
    }

    @Override
    protected void initialize() {
        drivetrain.stop();
        drivetrain.resetDistance();
        drivetrain.resetHeading();
        startTime = Timer.getFPGATimestamp();
    }

    @Override
    protected void execute() {
        double currentTime = Timer.getFPGATimestamp();
        PathFollower.followPath(drivetrain, path, timestep, currentTime - startTime);
    }

    @Override
    protected void end() {
        drivetrain.stop();
    }

    @Override
    protected void interrupted() {
        end();
    }

    @Override
    protected boolean isFinished() {
        int pos = (int) Math.floor((Timer.getFPGATimestamp() - startTime) / timestep);
        return pos >= path.getSourceTrajectory().length();
    }
}
