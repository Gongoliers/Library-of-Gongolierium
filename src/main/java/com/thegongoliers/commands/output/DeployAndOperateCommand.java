package com.thegongoliers.commands.output;

import com.thegongoliers.output.interfaces.Extendable;
import com.thegongoliers.output.interfaces.Lockable;
import com.thegongoliers.output.interfaces.Retractable;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.Subsystem;
import edu.wpi.first.wpilibj2.command.WaitCommand;

public class DeployAndOperateCommand extends SequentialCommandGroup {

    public DeployAndOperateCommand(Subsystem subsystem, Extendable extendable, Retractable retractable, Command operate) {
        addRequirements(subsystem);
        addCommands(
                new ExtendCommand(subsystem, extendable),
                operate,
                new RetractCommand(subsystem, retractable)
        );
    }

    public <T extends Subsystem & Retractable & Extendable> DeployAndOperateCommand(T subsystem, Command operate) {
        this(subsystem, subsystem, subsystem, operate);
    }

    public <T extends Retractable & Extendable> DeployAndOperateCommand(Subsystem subsystem, T component, Command operate) {
        this(subsystem, component, component, operate);
    }
}
