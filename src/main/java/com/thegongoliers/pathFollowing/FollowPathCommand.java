 package com.thegongoliers.pathFollowing;

 import com.thegongoliers.annotations.UsedInCompetition;
 import edu.wpi.first.wpilibj.command.CommandGroup;

 @UsedInCompetition(team = "5112", year = "2018")
 public class FollowPathCommand extends CommandGroup {

     /**
      * Creates a command to follow a path.
      * @param path The path to follow.
      */
     public FollowPathCommand(Path path){
        for (PathTaskCommand command: path){
            addSequential(command);
        }
     }
 }
