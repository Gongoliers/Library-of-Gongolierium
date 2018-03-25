package com.thegongoliers.input.odometry;

import edu.wpi.first.wpilibj.*;
import jaci.pathfinder.Pathfinder;
import jaci.pathfinder.Trajectory;
import jaci.pathfinder.Waypoint;
import jaci.pathfinder.modifiers.TankModifier;

import java.util.List;

public class PathFollower {


    public Trajectory generatePath(List<Waypoint> waypoints, double timestep, double maxVelocity, double maxAcceleration, double maxJerk){
        Trajectory.Config config = new Trajectory.Config(Trajectory.FitMethod.HERMITE_CUBIC, Trajectory.Config.SAMPLES_HIGH, timestep, maxVelocity, maxAcceleration, maxJerk);
        Waypoint[] points = new Waypoint[waypoints.size()];
        waypoints.toArray(points);
        return Pathfinder.generate(points, config);
    }

    public TankModifier generateTankTrajectory(List<Waypoint> waypoints, double timestep, double maxVelocity, double maxAcceleration, double maxJerk, double wheelbaseWidth){
        Trajectory trajectory = generatePath(waypoints, timestep, maxVelocity, maxAcceleration, maxJerk);
        TankModifier modifier = new TankModifier(trajectory);
        modifier.modify(wheelbaseWidth);

        return modifier;
    }

    public void followTrajectory(PIDController leftController, PIDController rightController, List<Waypoint> waypoints, double timestep, double currentTime){

    }


    public void followTrajectory(PIDController velocityController, Trajectory trajectory, double timestep, double currentTime){
        if(timestep == 0){
            return;
        }

        int pos = (int) Math.floor(currentTime / timestep);
        if (pos >= trajectory.length()){
            return;
        }

        Trajectory.Segment segment = trajectory.get(pos);

        speedMotorToVelocity(velocityController, segment.velocity);
    }

    private void speedMotorToVelocity(PIDController controller, double velocity){
        if(!controller.isEnabled())
            controller.enable();
        controller.setSetpoint(velocity);
    }


}
