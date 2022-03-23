package com.thegongoliers.commands.output;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.Subsystem;

import java.util.function.BooleanSupplier;

public class HomeCommand extends SequentialCommandGroup {

    public HomeCommand(Subsystem subsystem, Command move, BooleanSupplier atLimit, Command zeroSensors) {
        addRequirements(subsystem);
        addCommands(
                move.until(atLimit),
                zeroSensors
        );
    }

    public HomeCommand(Subsystem subsystem, Command move, BooleanSupplier atLimit, Runnable zeroSensors) {
        this(subsystem, move, atLimit, new InstantCommand(zeroSensors));
    }
}
