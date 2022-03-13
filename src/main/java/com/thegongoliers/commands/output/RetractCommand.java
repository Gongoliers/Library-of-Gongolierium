package com.thegongoliers.commands.output;

import com.thegongoliers.output.interfaces.Retractable;
import com.thegongoliers.output.interfaces.Stoppable;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.Subsystem;

public class RetractCommand extends CommandBase {

    private final Retractable retractable;

    public RetractCommand(Subsystem subsystem, Retractable retractable) {
        addRequirements(subsystem);
        this.retractable = retractable;
    }

    public <T extends Subsystem & Retractable> RetractCommand(T subsystem) {
        this(subsystem, subsystem);
    }

    @Override
    public void execute() {
        retractable.retract();
    }

    @Override
    public boolean isFinished() {
        return retractable.isRetracted();
    }

    @Override
    public void end(boolean interrupted) {
        if (retractable instanceof Stoppable) {
            ((Stoppable) retractable).stop();
        }
    }
}
