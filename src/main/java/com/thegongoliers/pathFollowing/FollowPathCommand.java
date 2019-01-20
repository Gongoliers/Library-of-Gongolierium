package com.thegongoliers.pathFollowing;

import com.thegongoliers.annotations.TestedBy;
import com.thegongoliers.math.PathWaypoint;
import edu.wpi.first.wpilibj.command.CommandGroup;

import java.util.List;

@TestedBy(team = "5112", year = "2018")
public class FollowPathCommand extends CommandGroup {

    /**
     * Creates a command to follow a path.
     * @param path The path to follow.
     */
    public FollowPathCommand(Path path){
        generatePath(path);
    }

    /**
     * Creates a command to follow a path.
     * @param drivetrain The robot drivetrain.
     * @param waypoints The waypoints in the path (assumes the robot starts at (0, 0) and is facing 90 degrees).
     */
    public FollowPathCommand(SmartDriveTrainSubsystem drivetrain, List<PathWaypoint> waypoints){
        Path path = new Path(drivetrain);
        double x = 0, y = 0, angle = 90;
        for (PathWaypoint waypoint: waypoints){
            // Rotate to face the next waypoint
            double angleToPosition = calculateAngle(x, y, waypoint.getX(), waypoint.getY());
            double rotation = angleToPosition - angle;
            angle += rotation;
            path.addRotation(rotation);

            // Drive to the next waypoint
            double distance = calculateDistance(x, y, waypoint.getX(), waypoint.getY());
            path.addStraightAway(distance);
            x = waypoint.getX();
            y = waypoint.getY();

            // Face the correct direction of the waypoint
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

    /**
     * Calculates the distance between two points.
     * @param x1 The starting x position.
     * @param y1 The starting y position.
     * @param x2 The ending x position.
     * @param y2 The ending y position.
     * @return The distance between the two points.
     */
    static double calculateDistance(double x1, double y1, double x2, double y2){
        return Math.sqrt(Math.pow(y2 - y1, 2) + Math.pow(x2 - x1, 2));
    }

    /**
     * Calculates the angle between two points in degrees.
     * @param x1 The starting x position.
     * @param y1 The starting y position.
     * @param x2 The ending x position.
     * @param y2 The ending y position.
     * @return The angle between the two points in degrees.
     */
    static double calculateAngle(double x1, double y1, double x2, double y2){
        return Math.toDegrees(Math.atan2(y2 - y1, x2 - x1));
    }

}
