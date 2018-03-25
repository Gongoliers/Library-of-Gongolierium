package com.thegongoliers.output;

import com.thegongoliers.pathFollowing.FollowSmoothPathCommand;
import com.thegongoliers.pathFollowing.SmartDriveTrainSubsystem;
import edu.wpi.first.wpilibj.command.CommandGroup;
import jaci.pathfinder.Waypoint;

import java.util.LinkedList;
import java.util.List;

public class TestFollowSmoothPath extends CommandGroup {

    private SmartDriveTrainSubsystem drivetrain;

    public TestFollowSmoothPath(){
        List<Waypoint> waypoints = new LinkedList<>();

        waypoints.add(new Waypoint(0, 0, 0));
        waypoints.add(new Waypoint(2.3, 1.5, 0));

        addSequential(new FollowSmoothPathCommand(drivetrain, waypoints));
    }

}
