package com.thegongoliers.commands.drivetrain;

import com.thegongoliers.output.drivetrain.ModularDrivetrain;
import com.thegongoliers.paths.SimplePath;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.Subsystem;

public class DriveToCommand extends SequentialCommandGroup {

    public DriveToCommand(Subsystem subsystem, ModularDrivetrain drivetrain, double distance) {
        SimplePath path = new SimplePath();
        path.addStraightAway(distance);
        addCommands(new FollowPathCommand(subsystem, drivetrain, path));
    }

}
