package com.thegongoliers.pathFollowing;

import com.thegongoliers.annotations.TestedBy;
import com.thegongoliers.math.PathWaypoint;
import edu.wpi.first.wpilibj.command.CommandGroup;

import java.util.List;

@TestedBy(team = "5112", year = "2018")
public class FollowPathCommand extends CommandGroup {

    public FollowPathCommand(Path path){
        generatePath(path);
    }

    public FollowPathCommand(SmartDriveTrainSubsystem drivetrain, List<PathWaypoint> waypoints){
        Path path = new Path(drivetrain);
        double x = 0, y = 0, angle = 90;
        for (PathWaypoint waypoint: waypoints){
            double angleToPosition = Math.atan2(waypoint.getY() - y, waypoint.getX() - x) * 180 / Math.PI;
            double rotation = angleToPosition - angle;
            angle += rotation;
            path.addRotation(rotation);

            double magnitude = Math.sqrt(Math.pow(waypoint.getY() - y, 2) + Math.pow(waypoint.getX() - x, 2));
            path.addStraightAway(magnitude);

            double finalAngle = waypoint.getHeading() - angle;
            angle += finalAngle;
            path.addRotation(finalAngle);
        }
        generatePath(path);
    }

    private void generatePath(Path path){
        for (PathTaskCommand command: path){
            addSequential(command);
        }
    }

}
