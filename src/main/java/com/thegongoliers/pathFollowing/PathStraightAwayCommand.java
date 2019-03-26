 package com.thegongoliers.pathFollowing;

 import com.thegongoliers.annotations.UsedInCompetition;
 import com.thegongoliers.input.odometry.DistanceSensor;
 import com.thegongoliers.output.interfaces.Drivetrain;
 import com.thegongoliers.pathFollowing.controllers.MotionController;
 import edu.wpi.first.wpilibj.command.Subsystem;


 @UsedInCompetition(team = "5112", year = "2018")
 public class PathStraightAwayCommand extends PathTaskCommand {

     private double distance;
     private DistanceSensor encoder;
     private MotionController motionController;

     public PathStraightAwayCommand(Subsystem subsystem, Drivetrain drivetrain, DistanceSensor encoder, MotionController controller, double distance) {
         super(subsystem, drivetrain);
         this.distance = distance;
         this.encoder = encoder;
         motionController = controller;
     }

     public PathStraightAwayCommand(Subsystem subsystem, Drivetrain drivetrain, DistanceSensor encoder, MotionController controller, double distance, double timeout) {
         super(subsystem, drivetrain, timeout);
         this.distance = distance;
         this.encoder = encoder;
         motionController = controller;
     }

     @Override
     protected void initialize() {
         distance += encoder.getDistance();
     }

     @Override
     protected void execute() {
         double currentDistance = encoder.getDistance();
         double pwm = motionController.calculate(currentDistance, distance);
         drivetrain.arcade(pwm, 0);
     }

     @Override
     protected boolean isFinished() {
         return motionController.isOnTarget(encoder.getDistance(), distance);
     }

     @Override
     public boolean equals(Object o) {
         if (this == o) return true;
         if (o == null || getClass() != o.getClass()) return false;
         PathStraightAwayCommand that = (PathStraightAwayCommand) o;
         return Double.compare(that.distance, distance) == 0;
     }

     @Override
     public String toString() {
         return "PathStraightAwayCommand{" +
                 "distance=" + distance +
                 '}';
     }
 }
