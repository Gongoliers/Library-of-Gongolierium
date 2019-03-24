 package com.thegongoliers.pathFollowing;

 import com.thegongoliers.input.odometry.IEncoder;
 import com.thegongoliers.math.PathWaypoint;
 import com.thegongoliers.output.interfaces.Drivetrain;
 import com.thegongoliers.pathFollowing.controllers.MotionController;
 import com.thegongoliers.pathFollowing.controllers.PID;
 import edu.wpi.first.wpilibj.command.Subsystem;
 import edu.wpi.first.wpilibj.interfaces.Gyro;
 import org.junit.Test;

 import java.util.Arrays;
 import java.util.List;

 import static org.mockito.Mockito.*;
 import static org.junit.Assert.*;

 public class FollowPathCommandTest {

     @Test
     public void calculatesRotationBetweenWaypoints(){

         // Works on axis
         double angle = FollowPathCommand.calculateAngle(0, 0, 1, 0);
         assertEquals(0, angle, 0.0001);

         angle = FollowPathCommand.calculateAngle(0, 0, -1, 0);
         assertEquals(180, angle, 0.0001);

         angle = FollowPathCommand.calculateAngle(0, 0, 0, 1);
         assertEquals(90, angle, 0.0001);

         angle = FollowPathCommand.calculateAngle(0, 0, 0, -1);
         assertEquals(270, angle, 0.0001);

         // Works on non-origin points
         angle = FollowPathCommand.calculateAngle(1, 2, 1, 1);
         assertEquals(270, angle, 0.0001);

         angle = FollowPathCommand.calculateAngle(1, 2, 2, 3);
         assertEquals(45, angle, 0.0001);

         angle = FollowPathCommand.calculateAngle(0, 0, -1, -1);
         assertEquals(225, angle, 0.0001);

         angle = FollowPathCommand.calculateAngle(0, 0, 1, -1);
         assertEquals(315, angle, 0.0001);

         // Works for the same point
         angle = FollowPathCommand.calculateAngle(1, 1, 1, 1);
         assertEquals(0, angle, 0.0001);

     }

     @Test
     public void calculatesShortestAngleBetweenAngles(){
         double angle = FollowPathCommand.calculateRotation(90, 90);
         assertEquals(0.0, angle, 0.0001);

         angle = FollowPathCommand.calculateRotation(90, 0);
         assertEquals(90.0, angle, 0.0001);

         angle = FollowPathCommand.calculateRotation(450, 0);
         assertEquals(90.0, angle, 0.0001);

         angle = FollowPathCommand.calculateRotation(450, 90);
         assertEquals(0.0, angle, 0.0001);

         angle = FollowPathCommand.calculateRotation(90, 270);
         assertEquals(-180, angle, 0.0001);

         angle = FollowPathCommand.calculateRotation(90, 225);
         assertEquals(-135, angle, 0.0001);

         angle = FollowPathCommand.calculateRotation(0, 225);
         assertEquals(135, angle, 0.0001);

         angle = FollowPathCommand.calculateRotation(225, 0);
         assertEquals(-135, angle, 0.0001);

         angle = FollowPathCommand.calculateRotation(-90, 0);
         assertEquals(-90, angle, 0.0001);

         angle = FollowPathCommand.calculateRotation(-180, 0);
         assertEquals(-180, angle, 0.0001);

         angle = FollowPathCommand.calculateRotation(-270, 0);
         assertEquals(90, angle, 0.0001);

         angle = FollowPathCommand.calculateRotation(-360, 0);
         assertEquals(0, angle, 0.0001);
     }

     @Test
     public void calculatesDistanceBetweenPoints(){
         // Works on axis
         double distance = FollowPathCommand.calculateDistance(0, 0, 1, 0);
         assertEquals(1, distance, 0.0001);

         distance = FollowPathCommand.calculateDistance(0, 0, -2, 0);
         assertEquals(2, distance, 0.0001);

         distance = FollowPathCommand.calculateDistance(0, 0, 0, 1);
         assertEquals(1, distance, 0.0001);

         distance = FollowPathCommand.calculateDistance(0, 0, 0, -2);
         assertEquals(2, distance, 0.0001);

         // Works on other points
         distance = FollowPathCommand.calculateDistance(1, 2, 1, -2);
         assertEquals(4, distance, 0.0001);

         distance = FollowPathCommand.calculateDistance(1, 1, 2, 2);
         assertEquals(Math.sqrt(2), distance, 0.0001);
     }

     @Test
     public void convertsPathWaypointsToPath(){
         // Drive forward, turn, drive forward, turn, drive forward, turn
         List<PathWaypoint> waypoints = Arrays.asList(
                 new PathWaypoint(0, 10),
                 new PathWaypoint(10, 10),
                 new PathWaypoint(0, 0),
                 new PathWaypoint(0, 0)
         );

         Subsystem subsystem = mock(Subsystem.class);
         Drivetrain drivetrain = mock(Drivetrain.class);
         Gyro gyro = mock(Gyro.class);
         IEncoder encoder = mock(IEncoder.class);
         MotionController controller = new PID(0, 0, 0, 0);


         Path outputPath = new Path(subsystem, drivetrain, gyro, encoder, controller, controller);
         outputPath.addStraightAway(10);
         outputPath.addRotation(90);
         outputPath.addStraightAway(10);
         outputPath.addRotation(135);
         outputPath.addStraightAway(Math.sqrt(200));
         outputPath.addRotation(135);

         assertEquals(outputPath, FollowPathCommand.waypointsToPath(subsystem, drivetrain, gyro, encoder, controller, controller, waypoints, 90));


         waypoints = Arrays.asList(
                 new PathWaypoint(0, 10),
                 new PathWaypoint(10, 10),
                 new PathWaypoint(0, 0)
         );

         outputPath = new Path(subsystem, drivetrain, gyro, encoder, controller, controller);
         outputPath.addStraightAway(10);
         outputPath.addRotation(90);
         outputPath.addStraightAway(10);
         outputPath.addRotation(135);
         outputPath.addStraightAway(Math.sqrt(200));
         outputPath.addRotation(-45);

         assertEquals(outputPath, FollowPathCommand.waypointsToPath(subsystem, drivetrain, gyro, encoder, controller, controller, waypoints, -90));

     }

 }