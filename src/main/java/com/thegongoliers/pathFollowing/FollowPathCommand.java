package com.thegongoliers.pathFollowing;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class FollowPathCommand extends CommandGroup {

    public FollowPathCommand(Path path){
        for (PathTaskCommand command: path){
            addSequential(command);
        }
    }

}
