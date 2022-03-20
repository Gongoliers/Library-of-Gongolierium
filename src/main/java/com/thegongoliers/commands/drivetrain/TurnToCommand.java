package com.thegongoliers.commands.drivetrain;

import com.thegongoliers.output.drivetrain.ModularDrivetrain;
import com.thegongoliers.paths.SimplePath;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.Subsystem;

public class TurnToCommand extends SequentialCommandGroup {

    public TurnToCommand(Subsystem subsystem, ModularDrivetrain drivetrain, double angle) {
        SimplePath path = new SimplePath();
        path.addRotation(angle);
        addCommands(new FollowPathCommand(subsystem, drivetrain, path));
    }

}
