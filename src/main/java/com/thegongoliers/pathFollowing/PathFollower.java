package com.thegongoliers.pathFollowing;

import jaci.pathfinder.Pathfinder;
import jaci.pathfinder.Trajectory;
import jaci.pathfinder.Waypoint;
import jaci.pathfinder.modifiers.TankModifier;

import java.util.List;

public class PathFollower {


    public static Trajectory generatePath(List<Waypoint> waypoints, double timestep, double maxVelocity, double maxAcceleration, double maxJerk){
        Trajectory.Config config = new Trajectory.Config(Trajectory.FitMethod.HERMITE_CUBIC, Trajectory.Config.SAMPLES_HIGH, timestep, maxVelocity, maxAcceleration, maxJerk);
        Waypoint[] points = new Waypoint[waypoints.size()];
        waypoints.toArray(points);
        return Pathfinder.generate(points, config);
    }

    public static TankModifier generateTankTrajectory(List<Waypoint> waypoints, double timestep, double maxVelocity, double maxAcceleration, double maxJerk, double wheelbaseWidth){
        Trajectory trajectory = generatePath(waypoints, timestep, maxVelocity, maxAcceleration, maxJerk);
        TankModifier modifier = new TankModifier(trajectory);
        modifier.modify(wheelbaseWidth);

        return modifier;
    }

    public static TankModifier generatePath(SmartDriveTrainSubsystem drivetrain, List<Waypoint> waypoints, double timestep){
        return generateTankTrajectory(waypoints, timestep, drivetrain.getMaxVelocity(), drivetrain.getMaxAcceleration(), drivetrain.getMaxJerk(), drivetrain.getWheelbaseWidth());
    }

    public static void followPath(SmartDriveTrainSubsystem drivetrain, TankModifier path, double timestep, double currentTime){
        double left = calculateSpeed(drivetrain.getLeftDistanceController(), path.getLeftTrajectory(), drivetrain.getLeftDistance(), timestep, currentTime);
        double right = calculateSpeed(drivetrain.getRightDistanceController(), path.getRightTrajectory(), drivetrain.getRightDistance(), timestep, currentTime);
        drivetrain.tank(left, right);
    }


    private static double calculateSpeed(MotionProfileController controller, Trajectory trajectory, double currentPosition, double timestep, double currentTime){
        if(timestep == 0){
            return 0;
        }

        int pos = (int) Math.floor(currentTime / timestep);
        if (pos >= trajectory.length()){
            return 0;
        }

        Trajectory.Segment segment = trajectory.get(pos);
        return controller.calculate(currentPosition, segment.position, segment.velocity, segment.acceleration);
    }
}
