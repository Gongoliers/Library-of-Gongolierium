// package com.thegongoliers.pathFollowing;

// import com.thegongoliers.annotations.TestedBy;
// import edu.wpi.first.wpilibj.command.Command;

// @TestedBy(team = "5112", year = "2018")
// public abstract class PathTaskCommand extends Command {

//     protected SmartDriveTrainSubsystem drivetrain;

//     public PathTaskCommand(SmartDriveTrainSubsystem drivetrain){
//         requires(drivetrain);
//         this.drivetrain = drivetrain;
//     }

//     public PathTaskCommand(SmartDriveTrainSubsystem drivetrain, double timeout){
//         requires(drivetrain);
//         this.drivetrain = drivetrain;
//         setTimeout(timeout);
//     }

//     @Override
//     abstract protected void execute();

//     @Override
//     abstract protected boolean isFinished();

//     @Override
//     protected void end() {
//         drivetrain.stop();
//     }

//     @Override
//     protected void interrupted() {
//         drivetrain.stop();
//     }
// }
