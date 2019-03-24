 package com.thegongoliers.pathFollowing;

 import com.thegongoliers.annotations.UsedInCompetition;
 import com.thegongoliers.output.interfaces.Drivetrain;
 import edu.wpi.first.wpilibj.command.Command;
 import edu.wpi.first.wpilibj.command.Subsystem;

 @UsedInCompetition(team = "5112", year = "2018")
 public abstract class PathTaskCommand extends Command {

     protected Drivetrain drivetrain;

     public PathTaskCommand(Subsystem subsystem, Drivetrain drivetrain){
         requires(subsystem);
         this.drivetrain = drivetrain;
     }

     public PathTaskCommand(Subsystem subsystem, Drivetrain drivetrain, double timeout){
         requires(subsystem);
         this.drivetrain = drivetrain;
         setTimeout(timeout);
     }

     @Override
     abstract protected void execute();

     @Override
     abstract protected boolean isFinished();

     @Override
     protected void end() {
         drivetrain.stop();
     }

     @Override
     protected void interrupted() {
         end();
     }
 }
