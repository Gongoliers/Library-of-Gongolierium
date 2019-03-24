 package com.thegongoliers.pathFollowing;

 import com.thegongoliers.annotations.UsedInCompetition;
 import com.thegongoliers.output.interfaces.Drivetrain;
 import com.thegongoliers.pathFollowing.controllers.MotionController;
 import edu.wpi.first.wpilibj.command.Subsystem;
 import edu.wpi.first.wpilibj.interfaces.Gyro;

 @UsedInCompetition(team = "5112", year = "2018")
 public class PathRotateCommand extends PathTaskCommand {

     private double angleDegrees;
     private Gyro gyro;
     private MotionController motionController;

     public PathRotateCommand(Subsystem subsystem, Drivetrain drivetrain, Gyro gyro, MotionController controller, double angleDegrees) {
         super(subsystem, drivetrain);
         this.angleDegrees = angleDegrees;
         this.gyro = gyro;
         motionController = controller;
     }

     public PathRotateCommand(Subsystem subsystem, Drivetrain drivetrain, Gyro gyro, MotionController controller, double angleDegrees, double timeout) {
         super(subsystem, drivetrain, timeout);
         this.angleDegrees = angleDegrees;
         this.gyro = gyro;
         motionController = controller;
     }

     @Override
     protected void initialize() {
         angleDegrees += gyro.getAngle();
     }

     @Override
     protected void execute() {
         double currentAngle = gyro.getAngle();
         double pwm = motionController.calculate(currentAngle, angleDegrees);
         drivetrain.arcade(0, pwm);
     }

     @Override
     protected boolean isFinished() {
         return motionController.isOnTarget(gyro.getAngle(), angleDegrees);
     }

     @Override
     public boolean equals(Object o) {
         if (this == o) return true;
         if (o == null || getClass() != o.getClass()) return false;
         PathRotateCommand that = (PathRotateCommand) o;
         return Double.compare(that.angleDegrees, angleDegrees) == 0;
     }

     @Override
     public String toString() {
         return "PathRotateCommand{" +
                 "angleDegrees=" + angleDegrees +
                 '}';
     }
 }
