// package com.thegongoliers.pathFollowing;

// import com.thegongoliers.annotations.TestedBy;

// import java.util.Objects;

// @TestedBy(team = "5112", year = "2018")
// public class PathStraightAwayCommand extends PathTaskCommand {

//     private double distance;

//     public PathStraightAwayCommand(SmartDriveTrainSubsystem drivetrain, double distance) {
//         super(drivetrain);
//         this.distance = distance;
//     }

//     public PathStraightAwayCommand(SmartDriveTrainSubsystem drivetrain, double distance, double timeout) {
//         super(drivetrain, timeout);
//         this.distance = distance;
//     }

//     @Override
//     protected void initialize() {
//         drivetrain.resetDistance();
//     }

//     @Override
//     protected void execute() {
//         double currentDistance = drivetrain.getCenterDistance();

//         drivetrain.tank(drivetrain.getLeftDistanceController().calculate(currentDistance, distance), drivetrain.getRightDistanceController().calculate(currentDistance, distance));
//     }

//     @Override
//     protected boolean isFinished() {
//         return drivetrain.getLeftDistanceController().isOnTarget(drivetrain.getCenterDistance(), distance);
//     }

//     @Override
//     protected void end() {
//         drivetrain.forward(0);
//     }

//     @Override
//     protected void interrupted() {
//         end();
//     }

//     @Override
//     public boolean equals(Object o) {
//         if (this == o) return true;
//         if (o == null || getClass() != o.getClass()) return false;
//         PathStraightAwayCommand that = (PathStraightAwayCommand) o;
//         return Double.compare(that.distance, distance) == 0;
//     }

//     @Override
//     public String toString() {
//         return "PathStraightAwayCommand{" +
//                 "distance=" + distance +
//                 '}';
//     }
// }
