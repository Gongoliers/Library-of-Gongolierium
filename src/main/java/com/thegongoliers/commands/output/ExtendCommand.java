package com.thegongoliers.commands.output;

import com.thegongoliers.output.interfaces.Extendable;
import com.thegongoliers.output.interfaces.Stoppable;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.Subsystem;

public class ExtendCommand extends CommandBase {

    private final Extendable extendable;

    public ExtendCommand(Subsystem subsystem, Extendable extendable) {
        addRequirements(subsystem);
        this.extendable = extendable;
    }

    public <T extends Subsystem & Extendable> ExtendCommand(T subsystem) {
        this(subsystem, subsystem);
    }

    @Override
    public void execute() {
        extendable.extend();
    }

    @Override
    public boolean isFinished() {
        return extendable.isExtended();
    }

    @Override
    public void end(boolean interrupted) {
        if (extendable instanceof Stoppable) {
            ((Stoppable) extendable).stop();
        }
    }
}
