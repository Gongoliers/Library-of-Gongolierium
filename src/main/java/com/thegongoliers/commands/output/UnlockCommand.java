package com.thegongoliers.commands.output;

import com.thegongoliers.output.interfaces.Lockable;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.Subsystem;

public class UnlockCommand extends InstantCommand {

    public UnlockCommand(Subsystem subsystem, Lockable lockable) {
        super(lockable::unlock, subsystem);
    }

    public <T extends Subsystem & Lockable> UnlockCommand(T subsystem) {
        this(subsystem, subsystem);
    }
}
