 package com.thegongoliers.pathFollowing;

 import com.thegongoliers.annotations.UsedInCompetition;
 import com.thegongoliers.input.odometry.DistanceSensor;
 import com.thegongoliers.math.PathWaypoint;
 import com.thegongoliers.output.interfaces.Drivetrain;
 import com.thegongoliers.pathFollowing.controllers.MotionController;
 import edu.wpi.first.wpilibj.command.CommandGroup;
 import edu.wpi.first.wpilibj.command.Subsystem;
 import edu.wpi.first.wpilibj.interfaces.Gyro;

 import java.util.List;

 @UsedInCompetition(team = "5112", year = "2018")
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
      * @param endingHeading The ending heading in degrees (0 to 360).
      */
     public FollowPathCommand(Subsystem subsystem, Drivetrain drivetrain, Gyro gyro, DistanceSensor encoder, MotionController turnController, MotionController straightController, List<PathWaypoint> waypoints, double endingHeading){
         Path path = waypointsToPath(subsystem, drivetrain, gyro, encoder, turnController, straightController, waypoints, endingHeading);
         generatePath(path);
     }

     private void generatePath(Path path){
         for (PathTaskCommand command: path){
             addSequential(command);
         }
     }

     static Path waypointsToPath(Subsystem subsystem, Drivetrain drivetrain, Gyro gyro, DistanceSensor encoder, MotionController turnController, MotionController straightController, List<PathWaypoint> waypoints, double endingHeading){
         Path path = new Path(subsystem, drivetrain, gyro, encoder, turnController, straightController);
         double x = 0, y = 0, angle = 90;
         for (PathWaypoint waypoint: waypoints){

             // Don't do anything if it is already at position
             if (waypoint.getX() == x && waypoint.getY() == y){
                 continue;
             }

             // Rotate to face the next waypoint
             double angleToPosition = calculateAngle(x, y, waypoint.getX(), waypoint.getY());
             if (angle != angleToPosition) {
                 double rotation = calculateRotation(angle, angleToPosition);
                 angle = angleToPosition;
                 path.addRotation(rotation);
             }

             // Drive to the next waypoint
             double distance = calculateDistance(x, y, waypoint.getX(), waypoint.getY());
             path.addStraightAway(distance);
             x = waypoint.getX();
             y = waypoint.getY();
         }

         // Rotate to final heading
         if (endingHeading != angle) {
             double finalAngle = calculateRotation(angle, endingHeading);
             path.addRotation(finalAngle);
         }
         return path;
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
      * Calculates the angle between two points in degrees (0 to 360 degrees).
      * @param x1 The starting x position.
      * @param y1 The starting y position.
      * @param x2 The ending x position.
      * @param y2 The ending y position.
      * @return The angle between the two points in degrees.
      */
     static double calculateAngle(double x1, double y1, double x2, double y2){
         double angle = Math.toDegrees(Math.atan2(y2 - y1, x2 - x1));

         if (angle < 0){
             angle = 360 + angle;
         }

         return angle;
     }


     /**
      * Calculates the shortest angle of rotation between the two angles. Positive is clockwise.
      * @param currentAngle The current angle in degrees.
      * @param targetAngle The target angle in degrees.
      * @return The shortest angle between the two angles.
      */
     static double calculateRotation(double currentAngle, double targetAngle){
         currentAngle %= 360;
         targetAngle %= 360;

         double difference = currentAngle - targetAngle;

         if (difference < -180){
             difference += 360;
         } else if (difference > 180){
             difference -= 360;
         }

         return difference;
     }

 }
